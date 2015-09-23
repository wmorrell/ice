package org.jbei.ice.lib.vo;

import org.jbei.ice.lib.dao.IDataTransferModel;
import org.jbei.ice.lib.models.SequenceFeature;

import java.util.LinkedList;
import java.util.List;

/**
 * Value object to hold a combination of {@link SequenceFeature} and
 * {@link org.jbei.ice.lib.models.Feature Feature} data.
 *
 * @author Zinovii Dmytriv, Timothy Ham
 */
public class DNAFeature implements IDataTransferModel {

    private static final long serialVersionUID = 1L;

    private long id;
    private String type = "";
    private String name = "";
    private int strand = 1;
    private String annotationType;
    private String uri;
    private String identifier;
    private List<DNAFeatureNote> notes = new LinkedList<>();
    private List<DNAFeatureLocation> locations = new LinkedList<>();

    public DNAFeature() {
        super();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DNAFeatureNote> getNotes() {
        return notes;
    }

    public void setNotes(List<DNAFeatureNote> notes) {
        this.notes = notes;
    }

    public int getStrand() {
        return strand;
    }

    public void setStrand(int strand) {
        this.strand = strand;
    }

    public void addNote(DNAFeatureNote dnaFeatureNote) {
        notes.add(dnaFeatureNote);
    }

    public String getAnnotationType() {
        return annotationType;
    }

    public void setAnnotationType(String annotationType) {
        this.annotationType = annotationType;
    }

    public void setLocations(List<DNAFeatureLocation> locations) {
        this.locations = locations;
    }

    public List<DNAFeatureLocation> getLocations() {
        return locations;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
