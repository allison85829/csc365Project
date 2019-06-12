package edu.calpoly.csc365.examples.dao1.view;

import javax.sql.RowSetListener;
import javax.sql.rowset.CachedRowSet;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class BookTableModel implements TableModel{
  CachedRowSet bookRowSet; // The ResultSet to interpret
  ResultSetMetaData metadata; // Additional information about the results

  int numcols, numrows; // How many rows and columns in the table

  public BookTableModel(CachedRowSet rowSetArg) throws SQLException {
    this.bookRowSet = rowSetArg;
    this.metadata = this.bookRowSet.getMetaData();
    numcols = metadata.getColumnCount();

    // Retrieve the number of rows.
    this.bookRowSet.beforeFirst();
    this.numrows = 0;
    while (this.bookRowSet.next()) {
      this.numrows++;
    }
    this.bookRowSet.beforeFirst();
  }

  public CachedRowSet getCustomerRowSet() {
    return this.bookRowSet;
  }

  /**
   * Returns the number of rows in the model. A
   * <code>JTable</code> uses this method to determine how many rows it
   * should display.  This method should be quick, as it
   * is called frequently during rendering.
   *
   * @return the number of rows in the model
   * @see #getColumnCount
   */
  public int getRowCount() {
    return numrows;
  }

  /**
   * Returns the number of columns in the model. A
   * <code>JTable</code> uses this method to determine how many columns it
   * should create and display by default.
   *
   * @return the number of columns in the model
   * @see #getRowCount
   */
  public int getColumnCount() {
    return numcols;
  }

  /**
   * Returns the name of the column at <code>columnIndex</code>.  This is used
   * to initialize the table's column header name.  Note: this name does
   * not need to be unique; two columns in a table can have the same name.
   *
   * @param columnIndex the index of the column
   * @return the name of the column
   */
  public String getColumnName(int columnIndex) {
    try {
      return metadata.getColumnName(columnIndex + 1);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Returns the most specific superclass for all the cell values
   * in the column.  This is used by the <code>JTable</code> to set up a
   * default renderer and editor for the column.
   *
   * @param columnIndex the index of the column
   * @return the common ancestor class of the object values in the model.
   */
  public Class<?> getColumnClass(int columnIndex) {
    return String.class;
  }

  /**
   * Returns true if the cell at <code>rowIndex</code> and
   * <code>columnIndex</code>
   * is editable.  Otherwise, <code>setValueAt</code> on the cell will not
   * change the value of that cell.
   *
   * @param rowIndex    the row whose value to be queried
   * @param columnIndex the column whose value to be queried
   * @return true if the cell is editable
   * @see #setValueAt
   */
  public boolean isCellEditable(int rowIndex, int columnIndex) {
    if (columnIndex == 0)
      return false;
    return true;
  }

  /**
   * Returns the value for the cell at <code>columnIndex</code> and
   * <code>rowIndex</code>.
   *
   * @param rowIndex    the row whose value is to be queried
   * @param columnIndex the column whose value is to be queried
   * @return the value Object at the specified cell
   */
  public Object getValueAt(int rowIndex, int columnIndex) {
    try {
      this.bookRowSet.absolute(rowIndex + 1);
      Object o = this.bookRowSet.getObject(columnIndex + 1);
      if (o == null)
        return null;
      else
        return o.toString();
    } catch (SQLException e) {
      return e.toString();
    }
  }

  /**
   * Sets the value in the cell at <code>columnIndex</code> and
   * <code>rowIndex</code> to <code>aValue</code>.
   *
   * @param aValue      the new value
   * @param rowIndex    the row whose value is to be changed
   * @param columnIndex the column whose value is to be changed
   * @see #getValueAt
   * @see #isCellEditable
   */
  public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    System.out.println("Calling setValueAt row " + rowIndex + ", column " + columnIndex);
    try {
      this.bookRowSet.absolute(rowIndex + 1);
      this.bookRowSet.updateString(columnIndex + 1, (String) aValue);
      this.bookRowSet.updateRow();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void deleteRow(int rowIndex) {
    System.out.println("Calling deleteRow row " + rowIndex);
    try {
      this.bookRowSet.absolute(rowIndex + 1);
      this.bookRowSet.deleteRow();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Adds a listener to the list that is notified each time a change
   * to the data model occurs.
   *
   * @param l the TableModelListener
   */
  public void addTableModelListener(TableModelListener l) {
  }

  /**
   * Removes a listener from the list that is notified each time a
   * change to the data model occurs.
   *
   * @param l the TableModelListener
   */
  public void removeTableModelListener(TableModelListener l) {

  }

  public void addEventHandlersToRowSet(RowSetListener listener) {
    this.bookRowSet.addRowSetListener(listener);
  }

  public void insertRow(Integer book_id, String title, 
  String author, String category, boolean availability ) throws SQLException {

    try {
      this.bookRowSet.moveToInsertRow();
      this.bookRowSet.updateInt("book_id", book_id);
      this.bookRowSet.updateString("title", title);
      this.bookRowSet.updateString("author", author);
      this.bookRowSet.updateString("category", category);
      this.bookRowSet.updateBoolean("availability", availability);
      this.bookRowSet.insertRow();
      this.bookRowSet.moveToCurrentRow();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      System.out.println(e.getSQLState());
      e.printStackTrace();
    }
  }

  public void close() {
    try {
    	bookRowSet.getStatement().close();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      System.out.println(e.getSQLState());
      e.printStackTrace();
    }
  }

  /** Automatically close when we're garbage collected */
  protected void finalize() {
    close();
  }
}
