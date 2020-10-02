package com.spaceprogram.db4o.sql.jdbc;

import com.db4o.reflect.ReflectClass;
import com.spaceprogram.db4o.sql.ObjectSetWrapper;
import com.spaceprogram.db4o.sql.Result;
import com.spaceprogram.db4o.sql.Converter;
import com.spaceprogram.db4o.sql.Sql4oException;

import java.sql.*;
import java.math.BigDecimal;
import java.io.InputStream;
import java.io.Reader;
import java.util.Map;
import java.util.Calendar;
import java.util.List;
import java.net.URL;
import java.lang.reflect.Field;

/**
 * User: treeder
 * Date: Jul 10, 2006
 * Time: 3:10:39 PM
 */
public class Db4oResultSet implements ResultSet {
    private ObjectSetWrapper results;
    private int index = -1;
    private boolean wasNull;

    public Db4oResultSet(List results) {
        this.results = (ObjectSetWrapper) results;
    }

    /**
     * Moves the cursor down one row from its current position.
     * A <code>ResultSet</code> cursor is initially positioned
     * before the first row; the first call to the method
     * <code>next</code> makes the first row the current row; the
     * second call makes the second row the current row, and so on.
     * <p/>
     * <P>If an input stream is open for the current row, a call
     * to the method <code>next</code> will
     * implicitly close it. A <code>ResultSet</code> object's
     * warning chain is cleared when a new row is read.
     *
     * @return <code>true</code> if the new current row is valid;
     *         <code>false</code> if there are no more rows
     * @throws java.sql.SQLException if a database access error occurs
     */
    public boolean next() throws SQLException {
        index++;
        return index < results.size() ? true: false;
    }

    /**
     * Releases this <code>ResultSet</code> object's database and
     * JDBC resources immediately instead of waiting for
     * this to happen when it is automatically closed.
     * <p/>
     * <P><B>Note:</B> A <code>ResultSet</code> object
     * is automatically closed by the
     * <code>Statement</code> object that generated it when
     * that <code>Statement</code> object is closed,
     * re-executed, or is used to retrieve the next result from a
     * sequence of multiple results. A <code>ResultSet</code> object
     * is also automatically closed when it is garbage collected.
     *
     * @throws java.sql.SQLException if a database access error occurs
     */
    public void close() throws SQLException {

    }

    /**
     * Reports whether
     * the last column read had a value of SQL <code>NULL</code>.
     * Note that you must first call one of the getter methods
     * on a column to try to read its value and then call
     * the method <code>wasNull</code> to see if the value read was
     * SQL <code>NULL</code>.
     *
     * @return <code>true</code> if the last column value read was SQL
     *         <code>NULL</code> and <code>false</code> otherwise
     * @throws java.sql.SQLException if a database access error occurs
     */
    public boolean wasNull() throws SQLException {
        return wasNull;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * a <code>String</code> in the Java programming language.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return the column value; if the value is SQL <code>NULL</code>, the
     *         value returned is <code>null</code>
     * @throws java.sql.SQLException if a database access error occurs
     */
    public String getString(int columnIndex) throws SQLException {
        // get current object
        Result result = getResult();
        try {
            return (String) setWasNull(result.getObject(columnIndex));
        } catch (Sql4oException e) {
            throw new SQLException(e.getMessage());
        }
    }

    private Result getResult() {
        Result result = (Result) results.get(index);
        return result;
    }

    private Object setWasNull(Object object) {
        if(object == null) wasNull = true;
        else wasNull = false;
        return object;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * a <code>boolean</code> in the Java programming language.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return the column value; if the value is SQL <code>NULL</code>, the
     *         value returned is <code>false</code>
     * @throws java.sql.SQLException if a database access error occurs
     */
    public boolean getBoolean(int columnIndex) throws SQLException {
        Result result = getResult();
        Boolean ret = null;
        try {
            ret = Converter.convertToBoolean(setWasNull(result.getObject(columnIndex)));
        } catch (Sql4oException e) {
            throw new SQLException(e.getMessage());
        }
        return ret;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * a <code>byte</code> in the Java programming language.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return the column value; if the value is SQL <code>NULL</code>, the
     *         value returned is <code>0</code>
     * @throws java.sql.SQLException if a database access error occurs
     */
    public byte getByte(int columnIndex) throws SQLException {
        return 0;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * a <code>short</code> in the Java programming language.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return the column value; if the value is SQL <code>NULL</code>, the
     *         value returned is <code>0</code>
     * @throws java.sql.SQLException if a database access error occurs
     */
    public short getShort(int columnIndex) throws SQLException {
        return 0;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * an <code>int</code> in the Java programming language.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return the column value; if the value is SQL <code>NULL</code>, the
     *         value returned is <code>0</code>
     * @throws java.sql.SQLException if a database access error occurs
     */
    public int getInt(int columnIndex) throws SQLException {
        return 0;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * a <code>long</code> in the Java programming language.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return the column value; if the value is SQL <code>NULL</code>, the
     *         value returned is <code>0</code>
     * @throws java.sql.SQLException if a database access error occurs
     */
    public long getLong(int columnIndex) throws SQLException {
        return 0;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * a <code>float</code> in the Java programming language.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return the column value; if the value is SQL <code>NULL</code>, the
     *         value returned is <code>0</code>
     * @throws java.sql.SQLException if a database access error occurs
     */
    public float getFloat(int columnIndex) throws SQLException {
        return 0;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * a <code>double</code> in the Java programming language.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return the column value; if the value is SQL <code>NULL</code>, the
     *         value returned is <code>0</code>
     * @throws java.sql.SQLException if a database access error occurs
     */
    public double getDouble(int columnIndex) throws SQLException {
        return 0;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * a <code>java.jdbc.BigDecimal</code> in the Java programming language.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param scale       the number of digits to the right of the decimal point
     * @return the column value; if the value is SQL <code>NULL</code>, the
     *         value returned is <code>null</code>
     * @throws java.sql.SQLException if a database access error occurs
     * @deprecated
     */
    @Deprecated
    public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
        return null;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * a <code>byte</code> array in the Java programming language.
     * The bytes represent the raw values returned by the driver.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return the column value; if the value is SQL <code>NULL</code>, the
     *         value returned is <code>null</code>
     * @throws java.sql.SQLException if a database access error occurs
     */
    public byte[] getBytes(int columnIndex) throws SQLException {
        return new byte[0];
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * a <code>java.jdbc.Date</code> object in the Java programming language.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return the column value; if the value is SQL <code>NULL</code>, the
     *         value returned is <code>null</code>
     * @throws java.sql.SQLException if a database access error occurs
     */
    public Date getDate(int columnIndex) throws SQLException {
        return null;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * a <code>java.jdbc.Time</code> object in the Java programming language.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return the column value; if the value is SQL <code>NULL</code>, the
     *         value returned is <code>null</code>
     * @throws java.sql.SQLException if a database access error occurs
     */
    public Time getTime(int columnIndex) throws SQLException {
        return null;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * a <code>java.jdbc.Timestamp</code> object in the Java programming language.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return the column value; if the value is SQL <code>NULL</code>, the
     *         value returned is <code>null</code>
     * @throws java.sql.SQLException if a database access error occurs
     */
    public Timestamp getTimestamp(int columnIndex) throws SQLException {
        return null;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * a stream of ASCII characters. The value can then be read in chunks from the
     * stream. This method is particularly
     * suitable for retrieving large <char>LONGVARCHAR</char> values.
     * The JDBC driver will
     * do any necessary conversion from the database format into ASCII.
     * <p/>
     * <P><B>Note:</B> All the data in the returned stream must be
     * read prior to getting the value of any other column. The next
     * call to a getter method implicitly closes the stream.  Also, a
     * stream may return <code>0</code> when the method
     * <code>InputStream.available</code>
     * is called whether there is data available or not.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return a Java input stream that delivers the database column value
     *         as a stream of one-byte ASCII characters;
     *         if the value is SQL <code>NULL</code>, the
     *         value returned is <code>null</code>
     * @throws java.sql.SQLException if a database access error occurs
     */
    public InputStream getAsciiStream(int columnIndex) throws SQLException {
        return null;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * as a stream of two-byte Unicode characters. The first byte is
     * the high byte; the second byte is the low byte.
     * <p/>
     * The value can then be read in chunks from the
     * stream. This method is particularly
     * suitable for retrieving large <code>LONGVARCHAR</code>values.  The
     * JDBC driver will do any necessary conversion from the database
     * format into Unicode.
     * <p/>
     * <P><B>Note:</B> All the data in the returned stream must be
     * read prior to getting the value of any other column. The next
     * call to a getter method implicitly closes the stream.
     * Also, a stream may return <code>0</code> when the method
     * <code>InputStream.available</code>
     * is called, whether there is data available or not.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return a Java input stream that delivers the database column value
     *         as a stream of two-byte Unicode characters;
     *         if the value is SQL <code>NULL</code>, the value returned is
     *         <code>null</code>
     * @throws java.sql.SQLException if a database access error occurs
     * @deprecated use <code>getCharacterStream</code> in place of
     *             <code>getUnicodeStream</code>
     */
    @Deprecated
    public InputStream getUnicodeStream(int columnIndex) throws SQLException {
        return null;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as a binary stream of
     * uninterpreted bytes. The value can then be read in chunks from the
     * stream. This method is particularly
     * suitable for retrieving large <code>LONGVARBINARY</code> values.
     * <p/>
     * <P><B>Note:</B> All the data in the returned stream must be
     * read prior to getting the value of any other column. The next
     * call to a getter method implicitly closes the stream.  Also, a
     * stream may return <code>0</code> when the method
     * <code>InputStream.available</code>
     * is called whether there is data available or not.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return a Java input stream that delivers the database column value
     *         as a stream of uninterpreted bytes;
     *         if the value is SQL <code>NULL</code>, the value returned is
     *         <code>null</code>
     * @throws java.sql.SQLException if a database access error occurs
     */
    public InputStream getBinaryStream(int columnIndex) throws SQLException {
        return null;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * a <code>String</code> in the Java programming language.
     *
     * @param columnName the SQL name of the column
     * @return the column value; if the value is SQL <code>NULL</code>, the
     *         value returned is <code>null</code>
     * @throws java.sql.SQLException if a database access error occurs
     */
    public String getString(String columnName) throws SQLException {
        // get current object
        Result result = getResult();
        try {
            return (String) result.getObject(columnName);
        } catch (Sql4oException e) {
            throw new SQLException(e.getMessage());
        }

    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * a <code>boolean</code> in the Java programming language.
     *
     * @param columnName the SQL name of the column
     * @return the column value; if the value is SQL <code>NULL</code>, the
     *         value returned is <code>false</code>
     * @throws java.sql.SQLException if a database access error occurs
     */
    public boolean getBoolean(String columnName) throws SQLException {
        return false;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * a <code>byte</code> in the Java programming language.
     *
     * @param columnName the SQL name of the column
     * @return the column value; if the value is SQL <code>NULL</code>, the
     *         value returned is <code>0</code>
     * @throws java.sql.SQLException if a database access error occurs
     */
    public byte getByte(String columnName) throws SQLException {
        return 0;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * a <code>short</code> in the Java programming language.
     *
     * @param columnName the SQL name of the column
     * @return the column value; if the value is SQL <code>NULL</code>, the
     *         value returned is <code>0</code>
     * @throws java.sql.SQLException if a database access error occurs
     */
    public short getShort(String columnName) throws SQLException {
        return 0;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * an <code>int</code> in the Java programming language.
     *
     * @param columnName the SQL name of the column
     * @return the column value; if the value is SQL <code>NULL</code>, the
     *         value returned is <code>0</code>
     * @throws java.sql.SQLException if a database access error occurs
     */
    public int getInt(String columnName) throws SQLException {
        return 0;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * a <code>long</code> in the Java programming language.
     *
     * @param columnName the SQL name of the column
     * @return the column value; if the value is SQL <code>NULL</code>, the
     *         value returned is <code>0</code>
     * @throws java.sql.SQLException if a database access error occurs
     */
    public long getLong(String columnName) throws SQLException {
        return 0;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * a <code>float</code> in the Java programming language.
     *
     * @param columnName the SQL name of the column
     * @return the column value; if the value is SQL <code>NULL</code>, the
     *         value returned is <code>0</code>
     * @throws java.sql.SQLException if a database access error occurs
     */
    public float getFloat(String columnName) throws SQLException {
        return 0;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * a <code>double</code> in the Java programming language.
     *
     * @param columnName the SQL name of the column
     * @return the column value; if the value is SQL <code>NULL</code>, the
     *         value returned is <code>0</code>
     * @throws java.sql.SQLException if a database access error occurs
     */
    public double getDouble(String columnName) throws SQLException {
        return 0;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * a <code>java.math.BigDecimal</code> in the Java programming language.
     *
     * @param columnName the SQL name of the column
     * @param scale      the number of digits to the right of the decimal point
     * @return the column value; if the value is SQL <code>NULL</code>, the
     *         value returned is <code>null</code>
     * @throws java.sql.SQLException if a database access error occurs
     * @deprecated
     */
    @Deprecated
    public BigDecimal getBigDecimal(String columnName, int scale) throws SQLException {
        return null;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * a <code>byte</code> array in the Java programming language.
     * The bytes represent the raw values returned by the driver.
     *
     * @param columnName the SQL name of the column
     * @return the column value; if the value is SQL <code>NULL</code>, the
     *         value returned is <code>null</code>
     * @throws java.sql.SQLException if a database access error occurs
     */
    public byte[] getBytes(String columnName) throws SQLException {
        return new byte[0];
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * a <code>java.jdbc.Date</code> object in the Java programming language.
     *
     * @param columnName the SQL name of the column
     * @return the column value; if the value is SQL <code>NULL</code>, the
     *         value returned is <code>null</code>
     * @throws java.sql.SQLException if a database access error occurs
     */
    public Date getDate(String columnName) throws SQLException {
        return null;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * a <code>java.jdbc.Time</code> object in the Java programming language.
     *
     * @param columnName the SQL name of the column
     * @return the column value;
     *         if the value is SQL <code>NULL</code>,
     *         the value returned is <code>null</code>
     * @throws java.sql.SQLException if a database access error occurs
     */
    public Time getTime(String columnName) throws SQLException {
        return null;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * a <code>java.jdbc.Timestamp</code> object.
     *
     * @param columnName the SQL name of the column
     * @return the column value; if the value is SQL <code>NULL</code>, the
     *         value returned is <code>null</code>
     * @throws java.sql.SQLException if a database access error occurs
     */
    public Timestamp getTimestamp(String columnName) throws SQLException {
        return null;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as a stream of
     * ASCII characters. The value can then be read in chunks from the
     * stream. This method is particularly
     * suitable for retrieving large <code>LONGVARCHAR</code> values.
     * The JDBC driver will
     * do any necessary conversion from the database format into ASCII.
     * <p/>
     * <P><B>Note:</B> All the data in the returned stream must be
     * read prior to getting the value of any other column. The next
     * call to a getter method implicitly closes the stream. Also, a
     * stream may return <code>0</code> when the method <code>available</code>
     * is called whether there is data available or not.
     *
     * @param columnName the SQL name of the column
     * @return a Java input stream that delivers the database column value
     *         as a stream of one-byte ASCII characters.
     *         If the value is SQL <code>NULL</code>,
     *         the value returned is <code>null</code>.
     * @throws java.sql.SQLException if a database access error occurs
     */
    public InputStream getAsciiStream(String columnName) throws SQLException {
        return null;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as a stream of two-byte
     * Unicode characters. The first byte is the high byte; the second
     * byte is the low byte.
     * <p/>
     * The value can then be read in chunks from the
     * stream. This method is particularly
     * suitable for retrieving large <code>LONGVARCHAR</code> values.
     * The JDBC technology-enabled driver will
     * do any necessary conversion from the database format into Unicode.
     * <p/>
     * <P><B>Note:</B> All the data in the returned stream must be
     * read prior to getting the value of any other column. The next
     * call to a getter method implicitly closes the stream.
     * Also, a stream may return <code>0</code> when the method
     * <code>InputStream.available</code> is called, whether there
     * is data available or not.
     *
     * @param columnName the SQL name of the column
     * @return a Java input stream that delivers the database column value
     *         as a stream of two-byte Unicode characters.
     *         If the value is SQL <code>NULL</code>, the value returned
     *         is <code>null</code>.
     * @throws java.sql.SQLException if a database access error occurs
     * @deprecated use <code>getCharacterStream</code> instead
     */
    @Deprecated
    public InputStream getUnicodeStream(String columnName) throws SQLException {
        return null;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as a stream of uninterpreted
     * <code>byte</code>s.
     * The value can then be read in chunks from the
     * stream. This method is particularly
     * suitable for retrieving large <code>LONGVARBINARY</code>
     * values.
     * <p/>
     * <P><B>Note:</B> All the data in the returned stream must be
     * read prior to getting the value of any other column. The next
     * call to a getter method implicitly closes the stream. Also, a
     * stream may return <code>0</code> when the method <code>available</code>
     * is called whether there is data available or not.
     *
     * @param columnName the SQL name of the column
     * @return a Java input stream that delivers the database column value
     *         as a stream of uninterpreted bytes;
     *         if the value is SQL <code>NULL</code>, the result is <code>null</code>
     * @throws java.sql.SQLException if a database access error occurs
     */
    public InputStream getBinaryStream(String columnName) throws SQLException {
        return null;
    }

    /**
     * Retrieves the first warning reported by calls on this
     * <code>ResultSet</code> object.
     * Subsequent warnings on this <code>ResultSet</code> object
     * will be chained to the <code>SQLWarning</code> object that
     * this method returns.
     * <p/>
     * <P>The warning chain is automatically cleared each time a new
     * row is read.  This method may not be called on a <code>ResultSet</code>
     * object that has been closed; doing so will cause an
     * <code>SQLException</code> to be thrown.
     * <p/>
     * <B>Note:</B> This warning chain only covers warnings caused
     * by <code>ResultSet</code> methods.  Any warning caused by
     * <code>Statement</code> methods
     * (such as reading OUT parameters) will be chained on the
     * <code>Statement</code> object.
     *
     * @return the first <code>SQLWarning</code> object reported or
     *         <code>null</code> if there are none
     * @throws java.sql.SQLException if a database access error occurs or this method is
     *                               called on a closed result set
     */
    public SQLWarning getWarnings() throws SQLException {
        return null;
    }

    /**
     * Clears all warnings reported on this <code>ResultSet</code> object.
     * After this method is called, the method <code>getWarnings</code>
     * returns <code>null</code> until a new warning is
     * reported for this <code>ResultSet</code> object.
     *
     * @throws java.sql.SQLException if a database access error occurs
     */
    public void clearWarnings() throws SQLException {

    }

    /**
     * Retrieves the name of the SQL cursor used by this <code>ResultSet</code>
     * object.
     * <p/>
     * <P>In SQL, a result table is retrieved through a cursor that is
     * named. The current row of a result set can be updated or deleted
     * using a positioned update/delete statement that references the
     * cursor name. To insure that the cursor has the proper isolation
     * level to support update, the cursor's <code>SELECT</code> statement
     * should be of the form <code>SELECT FOR UPDATE</code>. If
     * <code>FOR UPDATE</code> is omitted, the positioned updates may fail.
     * <p/>
     * <P>The JDBC API supports this SQL feature by providing the name of the
     * SQL cursor used by a <code>ResultSet</code> object.
     * The current row of a <code>ResultSet</code> object
     * is also the current row of this SQL cursor.
     * <p/>
     * <P><B>Note:</B> If positioned update is not supported, a
     * <code>SQLException</code> is thrown.
     *
     * @return the SQL name for this <code>ResultSet</code> object's cursor
     * @throws java.sql.SQLException if a database access error occurs
     */
    public String getCursorName() throws SQLException {
        return null;
    }

    /**
     * Retrieves the  number, types and properties of
     * this <code>ResultSet</code> object's columns.
     *
     * @return the description of this <code>ResultSet</code> object's columns
     * @throws java.sql.SQLException if a database access error occurs
     */
    public ResultSetMetaData getMetaData() throws SQLException {
        return null;
    }

    /**
     * <p>Gets the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * an <code>Object</code> in the Java programming language.
     * <p/>
     * <p>This method will return the value of the given column as a
     * Java object.  The type of the Java object will be the default
     * Java object type corresponding to the column's SQL type,
     * following the mapping for built-in types specified in the JDBC
     * specification. If the value is an SQL <code>NULL</code>,
     * the driver returns a Java <code>null</code>.
     * <p/>
     * <p>This method may also be used to read database-specific
     * abstract data types.
     * <p/>
     * In the JDBC 2.0 API, the behavior of method
     * <code>getObject</code> is extended to materialize
     * data of SQL user-defined types.  When a column contains
     * a structured or distinct value, the behavior of this method is as
     * if it were a call to: <code>getObject(columnIndex,
     * this.getStatement().getConnection().getTypeMap())</code>.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return a <code>java.lang.Object</code> holding the column value
     * @throws java.sql.SQLException if a database access error occurs
     */
    public Object getObject(int columnIndex) throws SQLException {
        return null;
    }

    /**
     * <p>Gets the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * an <code>Object</code> in the Java programming language.
     * <p/>
     * <p>This method will return the value of the given column as a
     * Java object.  The type of the Java object will be the default
     * Java object type corresponding to the column's SQL type,
     * following the mapping for built-in types specified in the JDBC
     * specification. If the value is an SQL <code>NULL</code>,
     * the driver returns a Java <code>null</code>.
     * <p/>
     * This method may also be used to read database-specific
     * abstract data types.
     * <p/>
     * In the JDBC 2.0 API, the behavior of the method
     * <code>getObject</code> is extended to materialize
     * data of SQL user-defined types.  When a column contains
     * a structured or distinct value, the behavior of this method is as
     * if it were a call to: <code>getObject(columnIndex,
     * this.getStatement().getConnection().getTypeMap())</code>.
     *
     * @param columnName the SQL name of the column
     * @return a <code>java.lang.Object</code> holding the column value
     * @throws java.sql.SQLException if a database access error occurs
     */
    public Object getObject(String columnName) throws SQLException {
        return null;
    }

    /**
     * Maps the given <code>ResultSet</code> column name to its
     * <code>ResultSet</code> column index.
     *
     * @param columnName the name of the column
     * @return the column index of the given column name
     * @throws java.sql.SQLException if the <code>ResultSet</code> object
     *                               does not contain <code>columnName</code> or a database access error occurs
     */
    public int findColumn(String columnName) throws SQLException {
        return 0;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as a
     * <code>java.io.Reader</code> object.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return a <code>java.io.Reader</code> object that contains the column
     *         value; if the value is SQL <code>NULL</code>, the value returned is
     *         <code>null</code> in the Java programming language.
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public Reader getCharacterStream(int columnIndex) throws SQLException {
        return null;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as a
     * <code>java.io.Reader</code> object.
     *
     * @param columnName the name of the column
     * @return a <code>java.io.Reader</code> object that contains the column
     *         value; if the value is SQL <code>NULL</code>, the value returned is
     *         <code>null</code> in the Java programming language
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public Reader getCharacterStream(String columnName) throws SQLException {
        return null;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as a
     * <code>java.math.BigDecimal</code> with full precision.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return the column value (full precision);
     *         if the value is SQL <code>NULL</code>, the value returned is
     *         <code>null</code> in the Java programming language.
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
        return null;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as a
     * <code>java.math.BigDecimal</code> with full precision.
     *
     * @param columnName the column name
     * @return the column value (full precision);
     *         if the value is SQL <code>NULL</code>, the value returned is
     *         <code>null</code> in the Java programming language.
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public BigDecimal getBigDecimal(String columnName) throws SQLException {
        return null;
    }

    /**
     * Retrieves whether the cursor is before the first row in
     * this <code>ResultSet</code> object.
     *
     * @return <code>true</code> if the cursor is before the first row;
     *         <code>false</code> if the cursor is at any other position or the
     *         result set contains no rows
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public boolean isBeforeFirst() throws SQLException {
        return false;
    }

    /**
     * Retrieves whether the cursor is after the last row in
     * this <code>ResultSet</code> object.
     *
     * @return <code>true</code> if the cursor is after the last row;
     *         <code>false</code> if the cursor is at any other position or the
     *         result set contains no rows
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public boolean isAfterLast() throws SQLException {
        return false;
    }

    /**
     * Retrieves whether the cursor is on the first row of
     * this <code>ResultSet</code> object.
     *
     * @return <code>true</code> if the cursor is on the first row;
     *         <code>false</code> otherwise
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public boolean isFirst() throws SQLException {
        return false;
    }

    /**
     * Retrieves whether the cursor is on the last row of
     * this <code>ResultSet</code> object.
     * Note: Calling the method <code>isLast</code> may be expensive
     * because the JDBC driver
     * might need to fetch ahead one row in order to determine
     * whether the current row is the last row in the result set.
     *
     * @return <code>true</code> if the cursor is on the last row;
     *         <code>false</code> otherwise
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public boolean isLast() throws SQLException {
        return false;
    }

    /**
     * Moves the cursor to the front of
     * this <code>ResultSet</code> object, just before the
     * first row. This method has no effect if the result set contains no rows.
     *
     * @throws java.sql.SQLException if a database access error
     *                               occurs or the result set type is <code>TYPE_FORWARD_ONLY</code>
     * @since 1.2
     */
    public void beforeFirst() throws SQLException {

    }

    /**
     * Moves the cursor to the end of
     * this <code>ResultSet</code> object, just after the
     * last row. This method has no effect if the result set contains no rows.
     *
     * @throws java.sql.SQLException if a database access error
     *                               occurs or the result set type is <code>TYPE_FORWARD_ONLY</code>
     * @since 1.2
     */
    public void afterLast() throws SQLException {

    }

    /**
     * Moves the cursor to the first row in
     * this <code>ResultSet</code> object.
     *
     * @return <code>true</code> if the cursor is on a valid row;
     *         <code>false</code> if there are no rows in the result set
     * @throws java.sql.SQLException if a database access error
     *                               occurs or the result set type is <code>TYPE_FORWARD_ONLY</code>
     * @since 1.2
     */
    public boolean first() throws SQLException {
        return false;
    }

    /**
     * Moves the cursor to the last row in
     * this <code>ResultSet</code> object.
     *
     * @return <code>true</code> if the cursor is on a valid row;
     *         <code>false</code> if there are no rows in the result set
     * @throws java.sql.SQLException if a database access error
     *                               occurs or the result set type is <code>TYPE_FORWARD_ONLY</code>
     * @since 1.2
     */
    public boolean last() throws SQLException {
        return false;
    }

    /**
     * Retrieves the current row number.  The first row is number 1, the
     * second number 2, and so on.
     *
     * @return the current row number; <code>0</code> if there is no current row
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public int getRow() throws SQLException {
        return 0;
    }

    /**
     * Moves the cursor to the given row number in
     * this <code>ResultSet</code> object.
     * <p/>
     * <p>If the row number is positive, the cursor moves to
     * the given row number with respect to the
     * beginning of the result set.  The first row is row 1, the second
     * is row 2, and so on.
     * <p/>
     * <p>If the given row number is negative, the cursor moves to
     * an absolute row position with respect to
     * the end of the result set.  For example, calling the method
     * <code>absolute(-1)</code> positions the
     * cursor on the last row; calling the method <code>absolute(-2)</code>
     * moves the cursor to the next-to-last row, and so on.
     * <p/>
     * <p>An attempt to position the cursor beyond the first/last row in
     * the result set leaves the cursor before the first row or after
     * the last row.
     * <p/>
     * <p><B>Note:</B> Calling <code>absolute(1)</code> is the same
     * as calling <code>first()</code>. Calling <code>absolute(-1)</code>
     * is the same as calling <code>last()</code>.
     *
     * @param row the number of the row to which the cursor should move.
     *            A positive number indicates the row number counting from the
     *            beginning of the result set; a negative number indicates the
     *            row number counting from the end of the result set
     * @return <code>true</code> if the cursor is on the result set;
     *         <code>false</code> otherwise
     * @throws java.sql.SQLException if a database access error
     *                               occurs, or the result set type is <code>TYPE_FORWARD_ONLY</code>
     * @since 1.2
     */
    public boolean absolute(int row) throws SQLException {
        return false;
    }

    /**
     * Moves the cursor a relative number of rows, either positive or negative.
     * Attempting to move beyond the first/last row in the
     * result set positions the cursor before/after the
     * the first/last row. Calling <code>relative(0)</code> is valid, but does
     * not change the cursor position.
     * <p/>
     * <p>Note: Calling the method <code>relative(1)</code>
     * is identical to calling the method <code>next()</code> and
     * calling the method <code>relative(-1)</code> is identical
     * to calling the method <code>previous()</code>.
     *
     * @param rows an <code>int</code> specifying the number of rows to
     *             move from the current row; a positive number moves the cursor
     *             forward; a negative number moves the cursor backward
     * @return <code>true</code> if the cursor is on a row;
     *         <code>false</code> otherwise
     * @throws java.sql.SQLException if a database access error occurs,
     *                               there is no current row, or the result set type is
     *                               <code>TYPE_FORWARD_ONLY</code>
     * @since 1.2
     */
    public boolean relative(int rows) throws SQLException {
        return false;
    }

    /**
     * Moves the cursor to the previous row in this
     * <code>ResultSet</code> object.
     *
     * @return <code>true</code> if the cursor is on a valid row;
     *         <code>false</code> if it is off the result set
     * @throws java.sql.SQLException if a database access error
     *                               occurs or the result set type is <code>TYPE_FORWARD_ONLY</code>
     * @since 1.2
     */
    public boolean previous() throws SQLException {
        return false;
    }

    /**
     * Gives a hint as to the direction in which the rows in this
     * <code>ResultSet</code> object will be processed.
     * The initial value is determined by the
     * <code>Statement</code> object
     * that produced this <code>ResultSet</code> object.
     * The fetch direction may be changed at any time.
     *
     * @param direction an <code>int</code> specifying the suggested
     *                  fetch direction; one of <code>ResultSet.FETCH_FORWARD</code>,
     *                  <code>ResultSet.FETCH_REVERSE</code>, or
     *                  <code>ResultSet.FETCH_UNKNOWN</code>
     * @throws java.sql.SQLException if a database access error occurs or
     *                               the result set type is <code>TYPE_FORWARD_ONLY</code> and the fetch
     *                               direction is not <code>FETCH_FORWARD</code>
     * @see java.sql.Statement#setFetchDirection
     * @see #getFetchDirection
     * @since 1.2
     */
    public void setFetchDirection(int direction) throws SQLException {

    }

    /**
     * Retrieves the fetch direction for this
     * <code>ResultSet</code> object.
     *
     * @return the current fetch direction for this <code>ResultSet</code> object
     * @throws java.sql.SQLException if a database access error occurs
     * @see #setFetchDirection
     * @since 1.2
     */
    public int getFetchDirection() throws SQLException {
        return 0;
    }

    /**
     * Gives the JDBC driver a hint as to the number of rows that should
     * be fetched from the database when more rows are needed for this
     * <code>ResultSet</code> object.
     * If the fetch size specified is zero, the JDBC driver
     * ignores the value and is free to make its own best guess as to what
     * the fetch size should be.  The default value is set by the
     * <code>Statement</code> object
     * that created the result set.  The fetch size may be changed at any time.
     *
     * @param rows the number of rows to fetch
     * @throws java.sql.SQLException if a database access error occurs or the
     *                               condition <code>0 <= rows <= Statement.getMaxRows()</code> is not satisfied
     * @see #getFetchSize
     * @since 1.2
     */
    public void setFetchSize(int rows) throws SQLException {

    }

    /**
     * Retrieves the fetch size for this
     * <code>ResultSet</code> object.
     *
     * @return the current fetch size for this <code>ResultSet</code> object
     * @throws java.sql.SQLException if a database access error occurs
     * @see #setFetchSize
     * @since 1.2
     */
    public int getFetchSize() throws SQLException {
        return 0;
    }

    /**
     * Retrieves the type of this <code>ResultSet</code> object.
     * The type is determined by the <code>Statement</code> object
     * that created the result set.
     *
     * @return <code>ResultSet.TYPE_FORWARD_ONLY</code>,
     *         <code>ResultSet.TYPE_SCROLL_INSENSITIVE</code>,
     *         or <code>ResultSet.TYPE_SCROLL_SENSITIVE</code>
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public int getType() throws SQLException {
        return 0;
    }

    /**
     * Retrieves the concurrency mode of this <code>ResultSet</code> object.
     * The concurrency used is determined by the
     * <code>Statement</code> object that created the result set.
     *
     * @return the concurrency type, either
     *         <code>ResultSet.CONCUR_READ_ONLY</code>
     *         or <code>ResultSet.CONCUR_UPDATABLE</code>
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public int getConcurrency() throws SQLException {
        return 0;
    }

    /**
     * Retrieves whether the current row has been updated.  The value returned
     * depends on whether or not the result set can detect updates.
     *
     * @return <code>true</code> if both (1) the row has been visibly updated
     *         by the owner or another and (2) updates are detected
     * @throws java.sql.SQLException if a database access error occurs
     * @see java.sql.DatabaseMetaData#updatesAreDetected
     * @since 1.2
     */
    public boolean rowUpdated() throws SQLException {
        return false;
    }

    /**
     * Retrieves whether the current row has had an insertion.
     * The value returned depends on whether or not this
     * <code>ResultSet</code> object can detect visible inserts.
     *
     * @return <code>true</code> if a row has had an insertion
     *         and insertions are detected; <code>false</code> otherwise
     * @throws java.sql.SQLException if a database access error occurs
     * @see java.sql.DatabaseMetaData#insertsAreDetected
     * @since 1.2
     */
    public boolean rowInserted() throws SQLException {
        return false;
    }

    /**
     * Retrieves whether a row has been deleted.  A deleted row may leave
     * a visible "hole" in a result set.  This method can be used to
     * detect holes in a result set.  The value returned depends on whether
     * or not this <code>ResultSet</code> object can detect deletions.
     *
     * @return <code>true</code> if a row was deleted and deletions are detected;
     *         <code>false</code> otherwise
     * @throws java.sql.SQLException if a database access error occurs
     * @see java.sql.DatabaseMetaData#deletesAreDetected
     * @since 1.2
     */
    public boolean rowDeleted() throws SQLException {
        return false;
    }

    /**
     * Gives a nullable column a null value.
     * <p/>
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code>
     * or <code>insertRow</code> methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public void updateNull(int columnIndex) throws SQLException {

    }

    /**
     * Updates the designated column with a <code>boolean</code> value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code> or
     * <code>insertRow</code> methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public void updateBoolean(int columnIndex, boolean x) throws SQLException {

    }

    /**
     * Updates the designated column with a <code>byte</code> value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code> or
     * <code>insertRow</code> methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public void updateByte(int columnIndex, byte x) throws SQLException {

    }

    /**
     * Updates the designated column with a <code>short</code> value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code> or
     * <code>insertRow</code> methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public void updateShort(int columnIndex, short x) throws SQLException {

    }

    /**
     * Updates the designated column with an <code>int</code> value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code> or
     * <code>insertRow</code> methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public void updateInt(int columnIndex, int x) throws SQLException {

    }

    /**
     * Updates the designated column with a <code>long</code> value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code> or
     * <code>insertRow</code> methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public void updateLong(int columnIndex, long x) throws SQLException {

    }

    /**
     * Updates the designated column with a <code>float</code> value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code> or
     * <code>insertRow</code> methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public void updateFloat(int columnIndex, float x) throws SQLException {

    }

    /**
     * Updates the designated column with a <code>double</code> value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code> or
     * <code>insertRow</code> methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public void updateDouble(int columnIndex, double x) throws SQLException {

    }

    /**
     * Updates the designated column with a <code>java.math.BigDecimal</code>
     * value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code> or
     * <code>insertRow</code> methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {

    }

    /**
     * Updates the designated column with a <code>String</code> value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code> or
     * <code>insertRow</code> methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public void updateString(int columnIndex, String x) throws SQLException {

    }

    /**
     * Updates the designated column with a <code>byte</code> array value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code> or
     * <code>insertRow</code> methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public void updateBytes(int columnIndex, byte x[]) throws SQLException {

    }

    /**
     * Updates the designated column with a <code>java.jdbc.Date</code> value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code> or
     * <code>insertRow</code> methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public void updateDate(int columnIndex, Date x) throws SQLException {

    }

    /**
     * Updates the designated column with a <code>java.jdbc.Time</code> value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code> or
     * <code>insertRow</code> methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public void updateTime(int columnIndex, Time x) throws SQLException {

    }

    /**
     * Updates the designated column with a <code>java.jdbc.Timestamp</code>
     * value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code> or
     * <code>insertRow</code> methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {

    }

    /**
     * Updates the designated column with an ascii stream value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code> or
     * <code>insertRow</code> methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @param length      the length of the stream
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {

    }

    /**
     * Updates the designated column with a binary stream value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code> or
     * <code>insertRow</code> methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @param length      the length of the stream
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {

    }

    /**
     * Updates the designated column with a character stream value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code> or
     * <code>insertRow</code> methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @param length      the length of the stream
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {

    }

    /**
     * Updates the designated column with an <code>Object</code> value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code> or
     * <code>insertRow</code> methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @param scale       for <code>java.jdbc.Types.DECIMA</code>
     *                    or <code>java.jdbc.Types.NUMERIC</code> types,
     *                    this is the number of digits after the decimal point.  For all other
     *                    types this value will be ignored.
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public void updateObject(int columnIndex, Object x, int scale) throws SQLException {

    }

    /**
     * Updates the designated column with an <code>Object</code> value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code> or
     * <code>insertRow</code> methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public void updateObject(int columnIndex, Object x) throws SQLException {

    }

    /**
     * Updates the designated column with a <code>null</code> value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code> or
     * <code>insertRow</code> methods are called to update the database.
     *
     * @param columnName the name of the column
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public void updateNull(String columnName) throws SQLException {

    }

    /**
     * Updates the designated column with a <code>boolean</code> value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code> or
     * <code>insertRow</code> methods are called to update the database.
     *
     * @param columnName the name of the column
     * @param x          the new column value
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public void updateBoolean(String columnName, boolean x) throws SQLException {

    }

    /**
     * Updates the designated column with a <code>byte</code> value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code> or
     * <code>insertRow</code> methods are called to update the database.
     *
     * @param columnName the name of the column
     * @param x          the new column value
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public void updateByte(String columnName, byte x) throws SQLException {

    }

    /**
     * Updates the designated column with a <code>short</code> value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code> or
     * <code>insertRow</code> methods are called to update the database.
     *
     * @param columnName the name of the column
     * @param x          the new column value
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public void updateShort(String columnName, short x) throws SQLException {

    }

    /**
     * Updates the designated column with an <code>int</code> value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code> or
     * <code>insertRow</code> methods are called to update the database.
     *
     * @param columnName the name of the column
     * @param x          the new column value
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public void updateInt(String columnName, int x) throws SQLException {

    }

    /**
     * Updates the designated column with a <code>long</code> value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code> or
     * <code>insertRow</code> methods are called to update the database.
     *
     * @param columnName the name of the column
     * @param x          the new column value
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public void updateLong(String columnName, long x) throws SQLException {

    }

    /**
     * Updates the designated column with a <code>float	</code> value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code> or
     * <code>insertRow</code> methods are called to update the database.
     *
     * @param columnName the name of the column
     * @param x          the new column value
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public void updateFloat(String columnName, float x) throws SQLException {

    }

    /**
     * Updates the designated column with a <code>double</code> value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code> or
     * <code>insertRow</code> methods are called to update the database.
     *
     * @param columnName the name of the column
     * @param x          the new column value
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public void updateDouble(String columnName, double x) throws SQLException {

    }

    /**
     * Updates the designated column with a <code>java.jdbc.BigDecimal</code>
     * value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code> or
     * <code>insertRow</code> methods are called to update the database.
     *
     * @param columnName the name of the column
     * @param x          the new column value
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public void updateBigDecimal(String columnName, BigDecimal x) throws SQLException {

    }

    /**
     * Updates the designated column with a <code>String</code> value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code> or
     * <code>insertRow</code> methods are called to update the database.
     *
     * @param columnName the name of the column
     * @param x          the new column value
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public void updateString(String columnName, String x) throws SQLException {

    }

    /**
     * Updates the designated column with a byte array value.
     * <p/>
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code>
     * or <code>insertRow</code> methods are called to update the database.
     *
     * @param columnName the name of the column
     * @param x          the new column value
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public void updateBytes(String columnName, byte x[]) throws SQLException {

    }

    /**
     * Updates the designated column with a <code>java.jdbc.Date</code> value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code> or
     * <code>insertRow</code> methods are called to update the database.
     *
     * @param columnName the name of the column
     * @param x          the new column value
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public void updateDate(String columnName, Date x) throws SQLException {

    }

    /**
     * Updates the designated column with a <code>java.jdbc.Time</code> value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code> or
     * <code>insertRow</code> methods are called to update the database.
     *
     * @param columnName the name of the column
     * @param x          the new column value
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public void updateTime(String columnName, Time x) throws SQLException {

    }

    /**
     * Updates the designated column with a <code>java.jdbc.Timestamp</code>
     * value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code> or
     * <code>insertRow</code> methods are called to update the database.
     *
     * @param columnName the name of the column
     * @param x          the new column value
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public void updateTimestamp(String columnName, Timestamp x) throws SQLException {

    }

    /**
     * Updates the designated column with an ascii stream value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code> or
     * <code>insertRow</code> methods are called to update the database.
     *
     * @param columnName the name of the column
     * @param x          the new column value
     * @param length     the length of the stream
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public void updateAsciiStream(String columnName, InputStream x, int length) throws SQLException {

    }

    /**
     * Updates the designated column with a binary stream value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code> or
     * <code>insertRow</code> methods are called to update the database.
     *
     * @param columnName the name of the column
     * @param x          the new column value
     * @param length     the length of the stream
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public void updateBinaryStream(String columnName, InputStream x, int length) throws SQLException {

    }

    /**
     * Updates the designated column with a character stream value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code> or
     * <code>insertRow</code> methods are called to update the database.
     *
     * @param columnName the name of the column
     * @param reader     the <code>java.io.Reader</code> object containing
     *                   the new column value
     * @param length     the length of the stream
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public void updateCharacterStream(String columnName, Reader reader, int length) throws SQLException {

    }

    /**
     * Updates the designated column with an <code>Object</code> value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code> or
     * <code>insertRow</code> methods are called to update the database.
     *
     * @param columnName the name of the column
     * @param x          the new column value
     * @param scale      for <code>java.jdbc.Types.DECIMAL</code>
     *                   or <code>java.jdbc.Types.NUMERIC</code> types,
     *                   this is the number of digits after the decimal point.  For all other
     *                   types this value will be ignored.
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public void updateObject(String columnName, Object x, int scale) throws SQLException {

    }

    /**
     * Updates the designated column with an <code>Object</code> value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code> or
     * <code>insertRow</code> methods are called to update the database.
     *
     * @param columnName the name of the column
     * @param x          the new column value
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public void updateObject(String columnName, Object x) throws SQLException {

    }

    /**
     * Inserts the contents of the insert row into this
     * <code>ResultSet</code> object and into the database.
     * The cursor must be on the insert row when this method is called.
     *
     * @throws java.sql.SQLException if a database access error occurs,
     *                               if this method is called when the cursor is not on the insert row,
     *                               or if not all of non-nullable columns in
     *                               the insert row have been given a value
     * @since 1.2
     */
    public void insertRow() throws SQLException {

    }

    /**
     * Updates the underlying database with the new contents of the
     * current row of this <code>ResultSet</code> object.
     * This method cannot be called when the cursor is on the insert row.
     *
     * @throws java.sql.SQLException if a database access error occurs or
     *                               if this method is called when the cursor is on the insert row
     * @since 1.2
     */
    public void updateRow() throws SQLException {

    }

    /**
     * Deletes the current row from this <code>ResultSet</code> object
     * and from the underlying database.  This method cannot be called when
     * the cursor is on the insert row.
     *
     * @throws java.sql.SQLException if a database access error occurs
     *                               or if this method is called when the cursor is on the insert row
     * @since 1.2
     */
    public void deleteRow() throws SQLException {

    }

    /**
     * Refreshes the current row with its most recent value in
     * the database.  This method cannot be called when
     * the cursor is on the insert row.
     * <p/>
     * <P>The <code>refreshRow</code> method provides a way for an
     * application to
     * explicitly tell the JDBC driver to refetch a row(s) from the
     * database.  An application may want to call <code>refreshRow</code> when
     * caching or prefetching is being done by the JDBC driver to
     * fetch the latest value of a row from the database.  The JDBC driver
     * may actually refresh multiple rows at once if the fetch size is
     * greater than one.
     * <p/>
     * <P> All values are refetched subject to the transaction isolation
     * level and cursor sensitivity.  If <code>refreshRow</code> is called after
     * calling an updater method, but before calling
     * the method <code>updateRow</code>, then the
     * updates made to the row are lost.  Calling the method
     * <code>refreshRow</code> frequently will likely slow performance.
     *
     * @throws java.sql.SQLException if a database access error
     *                               occurs or if this method is called when the cursor is on the insert row
     * @since 1.2
     */
    public void refreshRow() throws SQLException {

    }

    /**
     * Cancels the updates made to the current row in this
     * <code>ResultSet</code> object.
     * This method may be called after calling an
     * updater method(s) and before calling
     * the method <code>updateRow</code> to roll back
     * the updates made to a row.  If no updates have been made or
     * <code>updateRow</code> has already been called, this method has no
     * effect.
     *
     * @throws java.sql.SQLException if a database access error
     *                               occurs or if this method is called when the cursor is
     *                               on the insert row
     * @since 1.2
     */
    public void cancelRowUpdates() throws SQLException {

    }

    /**
     * Moves the cursor to the insert row.  The current cursor position is
     * remembered while the cursor is positioned on the insert row.
     * <p/>
     * The insert row is a special row associated with an updatable
     * result set.  It is essentially a buffer where a new row may
     * be constructed by calling the updater methods prior to
     * inserting the row into the result set.
     * <p/>
     * Only the updater, getter,
     * and <code>insertRow</code> methods may be
     * called when the cursor is on the insert row.  All of the columns in
     * a result set must be given a value each time this method is
     * called before calling <code>insertRow</code>.
     * An updater method must be called before a
     * getter method can be called on a column value.
     *
     * @throws java.sql.SQLException if a database access error occurs
     *                               or the result set is not updatable
     * @since 1.2
     */
    public void moveToInsertRow() throws SQLException {

    }

    /**
     * Moves the cursor to the remembered cursor position, usually the
     * current row.  This method has no effect if the cursor is not on
     * the insert row.
     *
     * @throws java.sql.SQLException if a database access error occurs
     *                               or the result set is not updatable
     * @since 1.2
     */
    public void moveToCurrentRow() throws SQLException {

    }

    /**
     * Retrieves the <code>Statement</code> object that produced this
     * <code>ResultSet</code> object.
     * If the result set was generated some other way, such as by a
     * <code>DatabaseMetaData</code> method, this method returns
     * <code>null</code>.
     *
     * @return the <code>Statment</code> object that produced
     *         this <code>ResultSet</code> object or <code>null</code>
     *         if the result set was produced some other way
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public Statement getStatement() throws SQLException {
        return null;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as an <code>Object</code>
     * in the Java programming language.
     * If the value is an SQL <code>NULL</code>,
     * the driver returns a Java <code>null</code>.
     * This method uses the given <code>Map</code> object
     * for the custom mapping of the
     * SQL structured or distinct type that is being retrieved.
     *
     * @param i   the first column is 1, the second is 2, ...
     * @param map a <code>java.util.Map</code> object that contains the mapping
     *            from SQL type names to classes in the Java programming language
     * @return an <code>Object</code> in the Java programming language
     *         representing the SQL value
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public Object getObject(int i, Map<String, Class<?>> map) throws SQLException {
        return null;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as a <code>Ref</code> object
     * in the Java programming language.
     *
     * @param i the first column is 1, the second is 2, ...
     * @return a <code>Ref</code> object representing an SQL <code>REF</code>
     *         value
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public Ref getRef(int i) throws SQLException {
        return null;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as a <code>Blob</code> object
     * in the Java programming language.
     *
     * @param i the first column is 1, the second is 2, ...
     * @return a <code>Blob</code> object representing the SQL
     *         <code>BLOB</code> value in the specified column
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public Blob getBlob(int i) throws SQLException {
        return null;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as a <code>Clob</code> object
     * in the Java programming language.
     *
     * @param i the first column is 1, the second is 2, ...
     * @return a <code>Clob</code> object representing the SQL
     *         <code>CLOB</code> value in the specified column
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public Clob getClob(int i) throws SQLException {
        return null;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as an <code>Array</code> object
     * in the Java programming language.
     *
     * @param i the first column is 1, the second is 2, ...
     * @return an <code>Array</code> object representing the SQL
     *         <code>ARRAY</code> value in the specified column
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public Array getArray(int i) throws SQLException {
        return null;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as an <code>Object</code>
     * in the Java programming language.
     * If the value is an SQL <code>NULL</code>,
     * the driver returns a Java <code>null</code>.
     * This method uses the specified <code>Map</code> object for
     * custom mapping if appropriate.
     *
     * @param colName the name of the column from which to retrieve the value
     * @param map     a <code>java.util.Map</code> object that contains the mapping
     *                from SQL type names to classes in the Java programming language
     * @return an <code>Object</code> representing the SQL value in the
     *         specified column
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public Object getObject(String colName, Map<String, Class<?>> map) throws SQLException {
        return null;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as a <code>Ref</code> object
     * in the Java programming language.
     *
     * @param colName the column name
     * @return a <code>Ref</code> object representing the SQL <code>REF</code>
     *         value in the specified column
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public Ref getRef(String colName) throws SQLException {
        return null;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as a <code>Blob</code> object
     * in the Java programming language.
     *
     * @param colName the name of the column from which to retrieve the value
     * @return a <code>Blob</code> object representing the SQL <code>BLOB</code>
     *         value in the specified column
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public Blob getBlob(String colName) throws SQLException {
        return null;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as a <code>Clob</code> object
     * in the Java programming language.
     *
     * @param colName the name of the column from which to retrieve the value
     * @return a <code>Clob</code> object representing the SQL <code>CLOB</code>
     *         value in the specified column
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public Clob getClob(String colName) throws SQLException {
        return null;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as an <code>Array</code> object
     * in the Java programming language.
     *
     * @param colName the name of the column from which to retrieve the value
     * @return an <code>Array</code> object representing the SQL <code>ARRAY</code> value in
     *         the specified column
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public Array getArray(String colName) throws SQLException {
        return null;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as a <code>java.jdbc.Date</code> object
     * in the Java programming language.
     * This method uses the given calendar to construct an appropriate millisecond
     * value for the date if the underlying database does not store
     * timezone information.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param cal         the <code>java.util.Calendar</code> object
     *                    to use in constructing the date
     * @return the column value as a <code>java.jdbc.Date</code> object;
     *         if the value is SQL <code>NULL</code>,
     *         the value returned is <code>null</code> in the Java programming language
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public Date getDate(int columnIndex, Calendar cal) throws SQLException {
        return null;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as a <code>java.jdbc.Date</code> object
     * in the Java programming language.
     * This method uses the given calendar to construct an appropriate millisecond
     * value for the date if the underlying database does not store
     * timezone information.
     *
     * @param columnName the SQL name of the column from which to retrieve the value
     * @param cal        the <code>java.util.Calendar</code> object
     *                   to use in constructing the date
     * @return the column value as a <code>java.jdbc.Date</code> object;
     *         if the value is SQL <code>NULL</code>,
     *         the value returned is <code>null</code> in the Java programming language
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public Date getDate(String columnName, Calendar cal) throws SQLException {
        return null;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as a <code>java.jdbc.Time</code> object
     * in the Java programming language.
     * This method uses the given calendar to construct an appropriate millisecond
     * value for the time if the underlying database does not store
     * timezone information.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param cal         the <code>java.util.Calendar</code> object
     *                    to use in constructing the time
     * @return the column value as a <code>java.jdbc.Time</code> object;
     *         if the value is SQL <code>NULL</code>,
     *         the value returned is <code>null</code> in the Java programming language
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public Time getTime(int columnIndex, Calendar cal) throws SQLException {
        return null;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as a <code>java.jdbc.Time</code> object
     * in the Java programming language.
     * This method uses the given calendar to construct an appropriate millisecond
     * value for the time if the underlying database does not store
     * timezone information.
     *
     * @param columnName the SQL name of the column
     * @param cal        the <code>java.util.Calendar</code> object
     *                   to use in constructing the time
     * @return the column value as a <code>java.jdbc.Time</code> object;
     *         if the value is SQL <code>NULL</code>,
     *         the value returned is <code>null</code> in the Java programming language
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public Time getTime(String columnName, Calendar cal) throws SQLException {
        return null;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as a <code>java.jdbc.Timestamp</code> object
     * in the Java programming language.
     * This method uses the given calendar to construct an appropriate millisecond
     * value for the timestamp if the underlying database does not store
     * timezone information.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param cal         the <code>java.util.Calendar</code> object
     *                    to use in constructing the timestamp
     * @return the column value as a <code>java.jdbc.Timestamp</code> object;
     *         if the value is SQL <code>NULL</code>,
     *         the value returned is <code>null</code> in the Java programming language
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
        return null;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as a <code>java.jdbc.Timestamp</code> object
     * in the Java programming language.
     * This method uses the given calendar to construct an appropriate millisecond
     * value for the timestamp if the underlying database does not store
     * timezone information.
     *
     * @param columnName the SQL name of the column
     * @param cal        the <code>java.util.Calendar</code> object
     *                   to use in constructing the date
     * @return the column value as a <code>java.jdbc.Timestamp</code> object;
     *         if the value is SQL <code>NULL</code>,
     *         the value returned is <code>null</code> in the Java programming language
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.2
     */
    public Timestamp getTimestamp(String columnName, Calendar cal) throws SQLException {
        return null;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as a <code>java.net.URL</code>
     * object in the Java programming language.
     *
     * @param columnIndex the index of the column 1 is the first, 2 is the second,...
     * @return the column value as a <code>java.net.URL</code> object;
     *         if the value is SQL <code>NULL</code>,
     *         the value returned is <code>null</code> in the Java programming language
     * @throws java.sql.SQLException if a database access error occurs,
     *                               or if a URL is malformed
     * @since 1.4
     */
    public URL getURL(int columnIndex) throws SQLException {
        return null;
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as a <code>java.net.URL</code>
     * object in the Java programming language.
     *
     * @param columnName the SQL name of the column
     * @return the column value as a <code>java.net.URL</code> object;
     *         if the value is SQL <code>NULL</code>,
     *         the value returned is <code>null</code> in the Java programming language
     * @throws java.sql.SQLException if a database access error occurs
     *                               or if a URL is malformed
     * @since 1.4
     */
    public URL getURL(String columnName) throws SQLException {
        return null;
    }

    /**
     * Updates the designated column with a <code>java.jdbc.Ref</code> value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code> or
     * <code>insertRow</code> methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.4
     */
    public void updateRef(int columnIndex, Ref x) throws SQLException {

    }

    /**
     * Updates the designated column with a <code>java.jdbc.Ref</code> value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code> or
     * <code>insertRow</code> methods are called to update the database.
     *
     * @param columnName the name of the column
     * @param x          the new column value
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.4
     */
    public void updateRef(String columnName, Ref x) throws SQLException {

    }

    /**
     * Updates the designated column with a <code>java.jdbc.Blob</code> value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code> or
     * <code>insertRow</code> methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.4
     */
    public void updateBlob(int columnIndex, Blob x) throws SQLException {

    }

    /**
     * Updates the designated column with a <code>java.jdbc.Blob</code> value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code> or
     * <code>insertRow</code> methods are called to update the database.
     *
     * @param columnName the name of the column
     * @param x          the new column value
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.4
     */
    public void updateBlob(String columnName, Blob x) throws SQLException {

    }

    /**
     * Updates the designated column with a <code>java.jdbc.Clob</code> value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code> or
     * <code>insertRow</code> methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.4
     */
    public void updateClob(int columnIndex, Clob x) throws SQLException {

    }

    /**
     * Updates the designated column with a <code>java.jdbc.Clob</code> value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code> or
     * <code>insertRow</code> methods are called to update the database.
     *
     * @param columnName the name of the column
     * @param x          the new column value
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.4
     */
    public void updateClob(String columnName, Clob x) throws SQLException {

    }

    /**
     * Updates the designated column with a <code>java.jdbc.Array</code> value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code> or
     * <code>insertRow</code> methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.4
     */
    public void updateArray(int columnIndex, Array x) throws SQLException {

    }

    /**
     * Updates the designated column with a <code>java.jdbc.Array</code> value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the <code>updateRow</code> or
     * <code>insertRow</code> methods are called to update the database.
     *
     * @param columnName the name of the column
     * @param x          the new column value
     * @throws java.sql.SQLException if a database access error occurs
     * @since 1.4
     */
    public void updateArray(String columnName, Array x) throws SQLException {

    }


    public int getHoldability() throws SQLException {
        return 0;
    }

    public boolean isClosed() throws SQLException {
        return false;
    }

    public void updateNString(int columnIndex, String nString) throws SQLException {

    }

    public void updateNString(String columnLabel, String nString) throws SQLException {

    }

    public String getNString(int columnIndex) throws SQLException {
        return null;
    }

    public String getNString(String columnLabel) throws SQLException {
        return null;
    }

    public Reader getNCharacterStream(int columnIndex) throws SQLException {
        return null;
    }

    public Reader getNCharacterStream(String columnLabel) throws SQLException {
        return null;
    }

    public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {

    }

    public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {

    }

    public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {

    }

    public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {

    }

    public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {

    }

    public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {

    }

    public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {

    }

    public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {

    }

    public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {

    }

    public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {

    }

    public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {

    }

    public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {

    }

    public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {

    }

    public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {

    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    //@Override
    public RowId getRowId(int i) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public RowId getRowId(String string) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public void updateRowId(int i, RowId rowid) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public void updateRowId(String string, RowId rowid) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public void updateNClob(int i, NClob nclob) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public void updateNClob(String string, NClob nclob) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public NClob getNClob(int i) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public NClob getNClob(String string) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public SQLXML getSQLXML(int i) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public SQLXML getSQLXML(String string) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public void updateSQLXML(int i, SQLXML sqlxml) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public void updateSQLXML(String string, SQLXML sqlxml) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public void updateNCharacterStream(int i, Reader reader) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public void updateNCharacterStream(String string, Reader reader) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public void updateAsciiStream(int i, InputStream in) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public void updateBinaryStream(int i, InputStream in) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public void updateCharacterStream(int i, Reader reader) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public void updateAsciiStream(String string, InputStream in) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public void updateBinaryStream(String string, InputStream in) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public void updateCharacterStream(String string, Reader reader) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public void updateBlob(int i, InputStream in) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public void updateBlob(String string, InputStream in) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public void updateClob(int i, Reader reader) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public void updateClob(String string, Reader reader) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public void updateNClob(int i, Reader reader) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public void updateNClob(String string, Reader reader) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public <T> T getObject(int i, Class<T> type) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public <T> T getObject(String string, Class<T> type) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public void updateObject(int i, Object o, SQLType sqlt, int i1) throws SQLException {
        ResultSet.super.updateObject(i, o, sqlt, i1); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public void updateObject(String string, Object o, SQLType sqlt, int i) throws SQLException {
        ResultSet.super.updateObject(string, o, sqlt, i); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public void updateObject(int i, Object o, SQLType sqlt) throws SQLException {
        ResultSet.super.updateObject(i, o, sqlt); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public void updateObject(String string, Object o, SQLType sqlt) throws SQLException {
        ResultSet.super.updateObject(string, o, sqlt); //To change body of generated methods, choose Tools | Templates.
    }
}
