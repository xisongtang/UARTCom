package com.ray;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
//import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

public class TableView extends JTable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6115487931497820370L;
	TableModel model;
	public TableView(TableModel model) {
		super(model);
		this.model = model;
		initComponent();
	}
	private void initComponent(){
		setTableHeader(null);
		TableColumnModel columnModel = getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(75);
		for (int i = 1; i != 9; ++i){
			columnModel.getColumn(i).setPreferredWidth(11);
		}
		columnModel.getColumn(9).setPreferredWidth(3);
		columnModel.getColumn(9).setMaxWidth(3);
		for (int i = 10; i != 18; ++i){
			columnModel.getColumn(i).setPreferredWidth(11);
		}
		columnModel.getColumn(18).setPreferredWidth(3);
		columnModel.getColumn(18).setMaxWidth(3);
		columnModel.getColumn(19).setPreferredWidth(60);
		columnModel.getColumn(20).setPreferredWidth(60);
		columnModel.getColumn(21).setPreferredWidth(20);	
		setColumnModel(columnModel);
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		setDefaultRenderer(Object.class, r);
	}
}
