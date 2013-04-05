package org.jbei.ice.client.bulkupload;

import org.jbei.ice.shared.dto.entry.EntryInfo;

/**
 * Delegate for retrieving entry info associated with a row
 *
 * @author Hector Plahar
 */
public interface EntryInfoDelegate {

    /**
     * @param row                        the row that is currently being worked on (and needs saving)
     * @param isStrainWithPlasmidPlasmid whether to retrieve the plasmid portion of a "strainWithPlasmid" add type
     * @return retrieved entryInfo
     */
    EntryInfo getInfoForRow(int row, boolean isStrainWithPlasmidPlasmid);
}