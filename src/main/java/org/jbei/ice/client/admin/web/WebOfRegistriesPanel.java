package org.jbei.ice.client.admin.web;

import java.util.ArrayList;
import java.util.HashMap;

import org.jbei.ice.client.ServiceDelegate;
import org.jbei.ice.client.admin.IAdminPanel;
import org.jbei.ice.client.common.widget.FAIconType;
import org.jbei.ice.shared.dto.ConfigurationKey;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

/**
 * @author Hector Plahar
 */
public class WebOfRegistriesPanel extends Composite implements IAdminPanel {

    private ServiceDelegate<String> addPartnerDelegate;
    private HTMLPanel addPartnerPanel;
    private final FlexTable layout;
    private FlexTable partnerPanel;
    private ListBox joinBox;
    private ArrayList<String> partnersList;

    public WebOfRegistriesPanel(ServiceDelegate<String> partnerDelegate) {
        layout = new FlexTable();
        layout.setWidth("500px");
        layout.setCellPadding(1);
        layout.setCellSpacing(0);
        initWidget(layout);

        joinBox = new ListBox();
        joinBox.addItem("Yes");
        joinBox.addItem("No");
        joinBox.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                partnerPanel.setVisible(joinBox.getSelectedIndex() == 0);
            }
        });

        this.addPartnerDelegate = partnerDelegate;
        this.partnersList = new ArrayList<String>();
    }

    public void setData(HashMap<String, String> settings) {
        layout.clear();
        layout.setHTML(0, 0, "Join web of registries");
        layout.getFlexCellFormatter().setWidth(0, 0, "180px");
        layout.setWidget(0, 1, joinBox);
        String value = settings.get(ConfigurationKey.JOIN_WEB_OF_REGISTRIES.name());
        partnerPanel = createRegistryPartnerPanel(settings);
        if (value == null || value.equalsIgnoreCase("no")) {
            joinBox.setSelectedIndex(1);
            partnerPanel.setVisible(false);
        } else
            joinBox.setSelectedIndex(0);

        layout.getFlexCellFormatter().setStyleName(0, 0, "pad_top");
        layout.getFlexCellFormatter().setStyleName(0, 1, "pad_top");

        layout.setWidget(1, 0, partnerPanel);
        layout.getFlexCellFormatter().setStyleName(1, 0, "pad_top");
        layout.getFlexCellFormatter().setColSpan(1, 0, 2);
    }

    public void addJoinBoxHandler(ChangeHandler handler) {
        joinBox.addChangeHandler(handler);
    }

    public String getJoinSelectedValue() {
        return joinBox.getSelectedIndex() == 0 ? "yes" : "no";
    }

    public void addPartner(String partner) {
        if (partnersList.isEmpty()) {
            partnerPanel.setHTML(2, 0, "<span style=\"margin-left: 10px; padding: 3px;\" class=\"font-75em\">"
                    + partner + "</span>");
        } else {
            int row = partnerPanel.getRowCount();
            partnerPanel.setHTML(row, 0, "<span style=\"margin-left: 10px; padding: 3px;\" class=\"font-75em\">"
                    + partner + "</span>");
        }
        partnersList.add(partner);
    }

    private FlexTable createRegistryPartnerPanel(HashMap<String, String> settings) {
        HTMLPanel headerPanel = new HTMLPanel(
                "<span style=\"color: #233559; "
                        + "font-weight: bold; font-style: italic; font-size: 0.80em; text-transform: uppercase\">"
                        + ConfigurationKey.WEB_PARTNERS.toString()
                        + "</span><span style=\"margin-left: 20px\" id=\"add_partner\"></span>");

        headerPanel.setStyleName("entry_sequence_sub_header");

        addPartnerPanel = new HTMLPanel("<span id=\"add_partner_input\"></span><span id=\"add_partner_submit\"></span>"
                                                + "<span id=\"add_partner_cancel\"></span>");
        final TextBox addInput = new TextBox();
        addInput.getElement().setAttribute("placeholder", "e.g. public-registry.jbei.org");
        addInput.setStyleName("input_box");
        addInput.setWidth("200px");

        addPartnerPanel.add(addInput, "add_partner_input");
        Button addPartnerSubmit = new Button("<i class=\"" + FAIconType.OK.getStyleName() + "\"></i>");

        addPartnerSubmit.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                String text = addInput.getText().trim();

                if (text.isEmpty()) {
                    addInput.setStyleName("input_box_error");
                    return;
                }

                addPartnerDelegate.execute(text);
                addInput.setStyleName("input_box");
                addInput.setText("");
                addPartnerPanel.setVisible(false);
            }
        });

        addPartnerPanel.add(addPartnerSubmit, "add_partner_submit");
        Button addPartnerCancel = new Button("<i class=\"" + FAIconType.REMOVE.getStyleName() + "\"></i>");
        addPartnerCancel.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                addPartnerPanel.setVisible(false);
            }
        });
        addPartnerPanel.add(addPartnerCancel, "add_partner_cancel");
        HTML addPartnerLabel = new HTML("<i class=\"" + FAIconType.GLOBE.getStyleName()
                                                + "\"></i><i style=\"vertical-align: text-bottom; font-size: 7px\""
                                                + "class=\"" + FAIconType.PLUS.getStyleName() + "\"></i>");
        addPartnerLabel.setStyleName("display-inline");
        headerPanel.add(addPartnerLabel, "add_partner");
        addPartnerLabel.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                addPartnerPanel.setVisible(true);
            }
        });

        FlexTable table = new FlexTable();
        table.setWidth("100%");
        table.setCellPadding(0);
        table.setCellSpacing(0);
        table.setWidget(0, 0, headerPanel);
        table.setWidget(1, 0, addPartnerPanel);
        addPartnerPanel.setVisible(false);

        // display partners
        String partners = settings.remove(ConfigurationKey.WEB_PARTNERS.name());
        if (partners == null || partners.isEmpty()) {
            table.setHTML(2, 0, "<div style=\"font-size: 0.85em; margin-top: 8px;\">No partners added.</div>");
        } else {
            int row = 2;
            for (String partner : partners.split(";")) {
                table.setHTML(row, 0, "<span style=\"margin-left: 10px; padding: 3px;\" class=\"font-75em\">"
                        + partner + "</span>");
                partnersList.add(partner);
                row += 1;
            }
        }
        return table;
    }
}