package org.jbei.ice.lib.bulkupload;

import org.jbei.ice.lib.common.logging.Logger;
import org.jbei.ice.lib.dto.entry.EntryType;
import org.jbei.ice.lib.entry.sequence.Sequences;
import org.jbei.ice.lib.parsers.sbol.ICESBOLParserVisitor;
import org.sbolstandard.core.SBOLDocument;
import org.sbolstandard.core.SBOLFactory;
import org.sbolstandard.core.SBOLRootObject;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

/**
 * Bulk upload with multiple components in a single SBOL document
 *
 * @author Hector Plahar
 */
public class BulkFileSBOLUpload {

    private final Path filePath;
    private final EntryType addType;
    private final String userId;

    public BulkFileSBOLUpload(String userId, Path path, EntryType addType) {
        this.userId = userId;
        this.filePath = path;
        this.addType = addType;
    }

    public long processUpload() throws IOException {
        BulkUploadController controller = new BulkUploadController();
        long bulkUploadId = 0;

        SBOLDocument document = SBOLFactory.read(new FileInputStream(filePath.toFile()));
        try {
            // walk top level object
            for (SBOLRootObject rootObject : document.getContents()) {
                ICESBOLParserVisitor visitor = new ICESBOLParserVisitor(addType);
                rootObject.accept(visitor);
                BulkUploadAutoUpdate update = visitor.getUpdate();
                update.setBulkUploadId(bulkUploadId);
                Logger.info(userId + ": " + update.toString());

                update = controller.autoUpdateBulkUpload(userId, update, addType);
                if (bulkUploadId == 0)
                    bulkUploadId = update.getBulkUploadId();

                // get "user sequence"

                // todo : sequence user
                String sequenceUser = getSequenceDocument(rootObject);
//                DNASequence dnaSequence = visitor.getFeaturedDNASequence();
//                Sequence sequence = Sequences.dnaSequenceToSequence(dnaSequence);


////                Entry entry = DAOFactory.getEntryDAO().get(entryId);
////                sequence.setEntry(entry);
//                if (sequenceUser != null)
//                    sequence.setSequenceUser(sequenceUser);
                Sequences sequences = new Sequences();
                sequences.save(userId, Long.toString(update.getEntryId()), visitor.getFeaturedDNASequence());
            }
        } catch (Exception e) {
            Logger.error(e);
            throw new IOException(e);
        }

        return bulkUploadId;
    }

    protected String getSequenceDocument(SBOLRootObject component) {
        SBOLDocument document = SBOLFactory.createDocument();
        document.addContent(component);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            SBOLFactory.write(document, stream);
        } catch (IOException e) {
            Logger.error(e);
            return null;
        }
        try {
            return stream.toString(StandardCharsets.UTF_8.name());
        } catch (IOException e) {
            Logger.error(e);
            return null;
        }
    }
}
