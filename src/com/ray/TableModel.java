package com.ray;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -793397721688739378L;
	private ArrayList<Byte> datas = new ArrayList<>();
	private byte[] bytes = new byte[8];
	public TableModel() {
		datas.add((byte)'a');
		datas.add((byte)'b');datas.add((byte)'a');
		datas.add((byte)'b');datas.add((byte)'a');
		datas.add((byte)'b');datas.add((byte)'a');
		datas.add((byte)'b');datas.add((byte)'a');
		datas.add((byte)'b');datas.add((byte)'a');
		datas.add((byte)'b');datas.add((byte)'a');
		datas.add((byte)'b');datas.add((byte)'a');
		datas.add((byte)'b');datas.add((byte)'a');
		datas.add((byte)'b');datas.add((byte)'a');
		datas.add((byte)'b');datas.add((byte)'a');
		datas.add((byte)'b');datas.add((byte)'a');
		datas.add((byte)'b');datas.add((byte)'a');
		datas.add((byte)'b');datas.add((byte)'a');
		datas.add((byte)'b');datas.add((byte)'a');
		datas.add((byte)'b');datas.add((byte)'a');
		datas.add((byte)'b');datas.add((byte)'a');
		datas.add((byte)'b');datas.add((byte)'a');
		datas.add((byte)'b');datas.add((byte)'a');
		datas.add((byte)'b');datas.add((byte)'a');
		datas.add((byte)'b');datas.add((byte)'a');
		datas.add((byte)'b');datas.add((byte)'a');
		datas.add((byte)'b');datas.add((byte)'a');
		datas.add((byte)'b');datas.add((byte)'a');
		datas.add((byte)'b');datas.add((byte)'a');
		datas.add((byte)'b');datas.add((byte)'a');
		datas.add((byte)'b');datas.add((byte)'a');
		datas.add((byte)'b');datas.add((byte)'a');
		datas.add((byte)'b');datas.add((byte)'a');
		datas.add((byte)'b');datas.add((byte)'a');
		datas.add((byte)'b');datas.add((byte)'a');
		datas.add((byte)'b');datas.add((byte)'a');
		datas.add((byte)'b');datas.add((byte)'a');
		datas.add((byte)'b');datas.add((byte)'a');
		datas.add((byte)'b');datas.add((byte)'a');
		datas.add((byte)'b');datas.add((byte)'a');
		datas.add((byte)'b');
	}
	@Override
	public int getColumnCount() {
		return 22;
	}

	@Override
	public int getRowCount() {
		return datas.size() / 16 + 1;
	}
	
	@Override
	public Object getValueAt(int row, int col) {
		try{
			if (col == 0){
				return String.format("%08x", row * 16).toUpperCase();
			}
			
			if (col < 9 && col > 0){
				return String.format("%02x", datas.get(row * 16 + col - 1)).toUpperCase();
			}
			if (col > 9 && col < 18){
				return String.format("%02x", datas.get(row * 16 + col - 2)).toUpperCase();
			}
			if (col == 19){
				for (int i = 0; i != 8; ++i)
					bytes[i] = 0; 
				for (int i = 0; i != 8 && row * 16 + i < datas.size(); ++i){
					bytes[i] = datas.get(row * 16 + i);
				}
			}
			else if (col == 20){
				for (int i = 0; i != 8; ++i)
					bytes[i] = 0;
				for (int i = 0; i != 8 && row * 16 + i + 8 < datas.size(); ++i){
					bytes[i] = datas.get(row * 16 + i + 8);
				}
			}
			else return "";
			for (int i = 0; i != 8; ++i){
				if (bytes[i] < 0x1f || bytes[i] == 0x7f)
					bytes[i] = '.';
			}
			return new String(bytes);
		}catch (IndexOutOfBoundsException e){
			return "";
		}
	}
	public void addValue(Byte data){
		datas.add(data);
		fireTableDataChanged();
	}
	public void clear(){
		datas.clear();
		fireTableDataChanged();
	}

}
