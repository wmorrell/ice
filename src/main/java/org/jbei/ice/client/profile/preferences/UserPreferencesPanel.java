package org.jbei.ice.client.profile.preferences;

import java.util.HashMap;

import org.jbei.ice.client.ServiceDelegate;
import org.jbei.ice.client.profile.widget.IUserProfilePanel;
import org.jbei.ice.shared.dto.user.PreferenceKey;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Hector Plahar
 */
public class UserPreferencesPanel extends Composite implements IUserProfilePanel {

    private final FlexTable layout;
    private ServiceDelegate<RowData> serviceDelegate;

    public UserPreferencesPanel() {
        layout = new FlexTable();
        initWidget(layout);
    }

    public void setData(HashMap<String, String> settings) {
        layout.clear();
        int row = 0;

        // general settings
        layout.setWidget(row, 0, createGeneralSettingPanel(settings));
        layout.getFlexCellFormatter().setColSpan(row, 0, 3);
        layout.getFlexCellFormatter().setStyleName(row, 0, "pad_top");
        row += 1;

        // search settings TODO
//        layout.setWidget(row, 0, createSearchSettings(settings));
//        layout.getFlexCellFormatter().setColSpan(row, 0, 3);
//        layout.getFlexCellFormatter().setStyleName(row, 0, "pad_top");
//        row += 1;
    }

    public void setServiceDelegate(ServiceDelegate<RowData> serviceDelegate) {
        this.serviceDelegate = serviceDelegate;
    }

    private Widget createGeneralSettingPanel(HashMap<String, String> settings) {
        return new PreferencesPanel(settings, "Create Entry Default Settings", serviceDelegate,
                                    PreferenceKey.PRINCIPAL_INVESTIGATOR,
                                    PreferenceKey.FUNDING_SOURCE);
    }

    private Widget createSearchSettings(HashMap<String, String> settings) {
        return new PreferencesPanel(settings, "Search Settings", serviceDelegate);
    }

    public void setConfigValue(PreferenceKey key, int row, String value) {
        for (int i = 0; i < layout.getRowCount(); i += 1) {
            Widget widget = layout.getWidget(i, 0);
            if (!(widget instanceof PreferencesPanel))
                continue;

            PreferencesPanel panel = (PreferencesPanel) widget;
            panel.updateConfigSetting(key, row, value);
        }
    }
}