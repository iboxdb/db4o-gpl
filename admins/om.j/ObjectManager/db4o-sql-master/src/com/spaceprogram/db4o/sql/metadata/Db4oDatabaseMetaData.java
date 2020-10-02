package com.spaceprogram.db4o.sql.metadata;

import com.spaceprogram.db4o.sql.jdbc.Db4oConnection;
import com.spaceprogram.db4o.sql.jdbc.Db4oDriver;
import com.spaceprogram.db4o.sql.jdbc.ListResultSet;
import com.spaceprogram.db4o.sql.util.ReflectHelper;
import com.db4o.reflect.ReflectClass;
import com.db4o.reflect.ReflectField;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.RowIdLifetime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * User: treeder
 * Date: Sep 4, 2006
 * Time: 5:00:38 PM
 */
public class Db4oDatabaseMetaData implements DatabaseMetaData {
    private Db4oDriver driver;
    private Db4oConnection db4oConnection;

    public Db4oDatabaseMetaData(Db4oDriver driver, Db4oConnection db4oConnection) {
        this.driver = driver;
        this.db4oConnection = db4oConnection;
    }

    public boolean allProceduresAreCallable() throws SQLException {
        return false;
    }

    public boolean allTablesAreSelectable() throws SQLException {
        return false;
    }

    public String getURL() throws SQLException {
        return null;
    }

    public String getUserName() throws SQLException {
        return null;
    }

    public boolean isReadOnly() throws SQLException {
        return false;
    }

    public boolean nullsAreSortedHigh() throws SQLException {
        return true;
    }

    public boolean nullsAreSortedLow() throws SQLException {
        return false;
    }

    public boolean nullsAreSortedAtStart() throws SQLException {
        return false;
    }

    public boolean nullsAreSortedAtEnd() throws SQLException {
        return false;
    }

    public String getDatabaseProductName() throws SQLException {
        return "db4o";
    }

    public String getDatabaseProductVersion() throws SQLException {
        return "5.X"; // todo: watch: http://tracker.db4o.com/jira/browse/COR-198
    }

    public String getDriverName() throws SQLException {
        return Db4oDriver.getDriverName();
    }

    public String getDriverVersion() throws SQLException {
        return Db4oDriver.getVersion();
    }

    public int getDriverMajorVersion() {
        return driver.getMajorVersion();
    }

    public int getDriverMinorVersion() {
        return driver.getMinorVersion();
    }

    public boolean usesLocalFiles() throws SQLException {
        return true;
    }

    public boolean usesLocalFilePerTable() throws SQLException {
        return false;
    }

    public boolean supportsMixedCaseIdentifiers() throws SQLException {
        return false;
    }

    public boolean storesUpperCaseIdentifiers() throws SQLException {
        return false;
    }

    public boolean storesLowerCaseIdentifiers() throws SQLException {
        return false;
    }

    public boolean storesMixedCaseIdentifiers() throws SQLException {
        return false;
    }

    public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException {
        return false;
    }

    public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
        return false;
    }

    public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
        return false;
    }

    public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
        return false;
    }

    public String getIdentifierQuoteString() throws SQLException {
        return " ";
    }

    public String getSQLKeywords() throws SQLException {
        return null;
    }

    public String getNumericFunctions() throws SQLException {
        return null;
    }

    public String getStringFunctions() throws SQLException {
        return null;
    }

    public String getSystemFunctions() throws SQLException {
        return null;
    }

    public String getTimeDateFunctions() throws SQLException {
        return null;
    }

    public String getSearchStringEscape() throws SQLException {
        return null;
    }

    public String getExtraNameCharacters() throws SQLException {
        return null;
    }

    public boolean supportsAlterTableWithAddColumn() throws SQLException {
        return false;
    }

    public boolean supportsAlterTableWithDropColumn() throws SQLException {
        return false;
    }

    public boolean supportsColumnAliasing() throws SQLException {
        return false;
    }

    public boolean nullPlusNonNullIsNull() throws SQLException {
        return false;
    }

    public boolean supportsConvert() throws SQLException {
        return false;
    }

    public boolean supportsConvert(int fromType, int toType) throws SQLException {
        return false;
    }

    public boolean supportsTableCorrelationNames() throws SQLException {
        return false;
    }

    public boolean supportsDifferentTableCorrelationNames() throws SQLException {
        return false;
    }

    public boolean supportsExpressionsInOrderBy() throws SQLException {
        return false;
    }

    public boolean supportsOrderByUnrelated() throws SQLException {
        return false;
    }

    public boolean supportsGroupBy() throws SQLException {
        return false;
    }

    public boolean supportsGroupByUnrelated() throws SQLException {
        return false;
    }

    public boolean supportsGroupByBeyondSelect() throws SQLException {
        return false;
    }

    public boolean supportsLikeEscapeClause() throws SQLException {
        return false;
    }

    public boolean supportsMultipleResultSets() throws SQLException {
        return false;
    }

    public boolean supportsMultipleTransactions() throws SQLException {
        return false;
    }

    public boolean supportsNonNullableColumns() throws SQLException {
        return false;
    }

    public boolean supportsMinimumSQLGrammar() throws SQLException {
        return false;
    }

    public boolean supportsCoreSQLGrammar() throws SQLException {
        return false;
    }

    public boolean supportsExtendedSQLGrammar() throws SQLException {
        return false;
    }

    public boolean supportsANSI92EntryLevelSQL() throws SQLException {
        return false;
    }

    public boolean supportsANSI92IntermediateSQL() throws SQLException {
        return false;
    }

    public boolean supportsANSI92FullSQL() throws SQLException {
        return false;
    }

    public boolean supportsIntegrityEnhancementFacility() throws SQLException {
        return false;
    }

    public boolean supportsOuterJoins() throws SQLException {
        return false;
    }

    public boolean supportsFullOuterJoins() throws SQLException {
        return false;
    }

    public boolean supportsLimitedOuterJoins() throws SQLException {
        return false;
    }

    public String getSchemaTerm() throws SQLException {
        return null;
    }

    public String getProcedureTerm() throws SQLException {
        return null;
    }

    public String getCatalogTerm() throws SQLException {
        return null;
    }

    public boolean isCatalogAtStart() throws SQLException {
        return false;
    }

    public String getCatalogSeparator() throws SQLException {
        return null;
    }

    public boolean supportsSchemasInDataManipulation() throws SQLException {
        return false;
    }

    public boolean supportsSchemasInProcedureCalls() throws SQLException {
        return false;
    }

    public boolean supportsSchemasInTableDefinitions() throws SQLException {
        return false;
    }

    public boolean supportsSchemasInIndexDefinitions() throws SQLException {
        return false;
    }

    public boolean supportsSchemasInPrivilegeDefinitions() throws SQLException {
        return false;
    }

    public boolean supportsCatalogsInDataManipulation() throws SQLException {
        return false;
    }

    public boolean supportsCatalogsInProcedureCalls() throws SQLException {
        return false;
    }

    public boolean supportsCatalogsInTableDefinitions() throws SQLException {
        return false;
    }

    public boolean supportsCatalogsInIndexDefinitions() throws SQLException {
        return false;
    }

    public boolean supportsCatalogsInPrivilegeDefinitions() throws SQLException {
        return false;
    }

    public boolean supportsPositionedDelete() throws SQLException {
        return false;
    }

    public boolean supportsPositionedUpdate() throws SQLException {
        return false;
    }

    public boolean supportsSelectForUpdate() throws SQLException {
        return false;
    }

    public boolean supportsStoredProcedures() throws SQLException {
        return false;
    }

    public boolean supportsSubqueriesInComparisons() throws SQLException {
        return false;
    }

    public boolean supportsSubqueriesInExists() throws SQLException {
        return false;
    }

    public boolean supportsSubqueriesInIns() throws SQLException {
        return false;
    }

    public boolean supportsSubqueriesInQuantifieds() throws SQLException {
        return false;
    }

    public boolean supportsCorrelatedSubqueries() throws SQLException {
        return false;
    }

    public boolean supportsUnion() throws SQLException {
        return false;
    }

    public boolean supportsUnionAll() throws SQLException {
        return false;
    }

    public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
        return false;
    }

    public boolean supportsOpenCursorsAcrossRollback() throws SQLException {
        return false;
    }

    public boolean supportsOpenStatementsAcrossCommit() throws SQLException {
        return false;
    }

    public boolean supportsOpenStatementsAcrossRollback() throws SQLException {
        return false;
    }

    public int getMaxBinaryLiteralLength() throws SQLException {
        return 0;
    }

    public int getMaxCharLiteralLength() throws SQLException {
        return 0;
    }

    public int getMaxColumnNameLength() throws SQLException {
        return 0;
    }

    public int getMaxColumnsInGroupBy() throws SQLException {
        return 0;
    }

    public int getMaxColumnsInIndex() throws SQLException {
        return 0;
    }

    public int getMaxColumnsInOrderBy() throws SQLException {
        return 0;
    }

    public int getMaxColumnsInSelect() throws SQLException {
        return 0;
    }

    public int getMaxColumnsInTable() throws SQLException {
        return 0;
    }

    public int getMaxConnections() throws SQLException {
        return 0;
    }

    public int getMaxCursorNameLength() throws SQLException {
        return 0;
    }

    public int getMaxIndexLength() throws SQLException {
        return 0;
    }

    public int getMaxSchemaNameLength() throws SQLException {
        return 0;
    }

    public int getMaxProcedureNameLength() throws SQLException {
        return 0;
    }

    public int getMaxCatalogNameLength() throws SQLException {
        return 0;
    }

    public int getMaxRowSize() throws SQLException {
        return 0;
    }

    public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
        return false;
    }

    public int getMaxStatementLength() throws SQLException {
        return 0;
    }

    public int getMaxStatements() throws SQLException {
        return 0;
    }

    public int getMaxTableNameLength() throws SQLException {
        return 0;
    }

    public int getMaxTablesInSelect() throws SQLException {
        return 0;
    }

    public int getMaxUserNameLength() throws SQLException {
        return 0;
    }

    public int getDefaultTransactionIsolation() throws SQLException {
        return 0;
    }

    public boolean supportsTransactions() throws SQLException {
        return false;
    }

    public boolean supportsTransactionIsolationLevel(int level) throws SQLException {
        return false;
    }

    public boolean supportsDataDefinitionAndDataManipulationTransactions() throws SQLException {
        return false;
    }

    public boolean supportsDataManipulationTransactionsOnly() throws SQLException {
        return false;
    }

    public boolean dataDefinitionCausesTransactionCommit() throws SQLException {
        return false;
    }

    public boolean dataDefinitionIgnoredInTransactions() throws SQLException {
        return false;
    }

    public ResultSet getProcedures(String catalog, String schemaPattern, String procedureNamePattern) throws SQLException {
        return null;
    }

    public ResultSet getProcedureColumns(String catalog, String schemaPattern, String procedureNamePattern, String columnNamePattern) throws SQLException {
        return null;
    }

    /**
     * Retrieves a description of the tables available in the given catalog. Only table descriptions matching the catalog, schema, table name and type criteria are returned. They are ordered by TABLE_TYPE, TABLE_SCHEM and TABLE_NAME.
     * Each table description has the following columns:
     * TABLE_CAT String => table catalog (may be null)
     * TABLE_SCHEM String => table schema (may be null)
     * TABLE_NAME String => table name
     * TABLE_TYPE String => table type. Typical types are "TABLE", "VIEW", "SYSTEM TABLE", "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "ALIAS", "SYNONYM".
     * REMARKS String => explanatory comment on the table
     * TYPE_CAT String => the types catalog (may be null)
     * TYPE_SCHEM String => the types schema (may be null)
     * TYPE_NAME String => type name (may be null)
     * SELF_REFERENCING_COL_NAME String => name of the designated "identifier" column of a typed table (may be null)
     * REF_GENERATION String => specifies how values in SELF_REFERENCING_COL_NAME are created. Values are "SYSTEM", "USER", "DERIVED". (may be null)
     *
     * @param catalog
     * @param schemaPattern
     * @param tableNamePattern
     * @param types
     * @return
     * @throws SQLException
     */
    public ResultSet getTables(String catalog, String schemaPattern, String tableNamePattern, String types[]) throws SQLException {
        ListResultSet<Map<String, String>> rs = new ListResultSet();
        List<ReflectClass> storedClasses = ReflectHelper.getUserStoredClasses(db4oConnection.getObjectContainer());
        for (int i = 0; i < storedClasses.size(); i++) {
            ReflectClass o = storedClasses.get(i);
            Map<String, String> row = new HashMap();
            row.put("TABLE_NAME", o.getName());
            row.put("TABLE_TYPE", "TABLE");
            rs.add(row);
        }
        return rs;
    }

    public ResultSet getSchemas() throws SQLException {
        ListResultSet<Map<String, String>> rs = new ListResultSet();
        Map<String, String> map = new HashMap();
        map.put("TABLE_SCHEM", "MAIN");
        rs.add(map);
        return rs;
    }

    public ResultSet getCatalogs() throws SQLException {
        ListResultSet<Map<String, String>> rs = new ListResultSet();
        Map<String, String> map = new HashMap();
        map.put("TABLE_CAT", "MAIN");
        rs.add(map);
        return rs;
    }

    public ResultSet getTableTypes() throws SQLException {
        ListResultSet<Map<String, String>> rs = new ListResultSet();
        Map<String, String> map = new HashMap();
        map.put("TABLE_TYPE", "TABLE");
        rs.add(map);
        return rs;
    }


    public ResultSet getColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern) throws SQLException {
        ListResultSet<Map<String, Object>> rs = new ListResultSet();
        List<ReflectClass> storedClasses = ReflectHelper.getUserStoredClasses(db4oConnection.getObjectContainer());
        for (int i = 0; i < storedClasses.size(); i++) {
            ReflectClass reflectClass = storedClasses.get(i);
            ReflectField[] fields = ReflectHelper.getDeclaredFieldsInHeirarchy(reflectClass);
            for (int j = 0; j < fields.length; j++) {
                ReflectField field = fields[j];
                Map<String, Object> row = new HashMap();
                row.put("TABLE_NAME", reflectClass.getName());
                row.put("COLUMN_NAME", field.getName());
                row.put("DATA_TYPE", getSqlType(field.getFieldType()));
                row.put("TYPE_NAME", "String");
                row.put("COLUMN_SIZE", 32);
                row.put("NULLABLE", DatabaseMetaData.columnNullable);
                row.put("CHAR_OCTET_LENGTH", 1024);
                row.put("ORDINAL_POSITION", j);
                row.put("IS_NULLABLE", "YES");
                rs.add(row);
            }
        }
        return rs;
    }

    private int getSqlType(ReflectClass fieldType) {
        return java.sql.Types.VARCHAR;
    }

    public ResultSet getColumnPrivileges(String catalog, String schema, String table, String columnNamePattern) throws SQLException {
        return null;
    }

    public ResultSet getTablePrivileges(String catalog, String schemaPattern, String tableNamePattern) throws SQLException {
        return null;
    }

    public ResultSet getBestRowIdentifier(String catalog, String schema, String table, int scope, boolean nullable) throws SQLException {
        return null;
    }

    public ResultSet getVersionColumns(String catalog, String schema, String table) throws SQLException {
        return null;
    }

    public ResultSet getPrimaryKeys(String catalog, String schema, String table) throws SQLException {
        return null;
    }

    public ResultSet getImportedKeys(String catalog, String schema, String table) throws SQLException {
        return null;
    }

    public ResultSet getExportedKeys(String catalog, String schema, String table) throws SQLException {
        return null;
    }

    public ResultSet getCrossReference(String primaryCatalog, String primarySchema, String primaryTable, String foreignCatalog, String foreignSchema, String foreignTable) throws SQLException {
        return null;
    }

    public ResultSet getTypeInfo() throws SQLException {
        return null;
    }

    public ResultSet getIndexInfo(String catalog, String schema, String table, boolean unique, boolean approximate) throws SQLException {
        return null;
    }

    public boolean supportsResultSetType(int type) throws SQLException {
        return false;
    }

    public boolean supportsResultSetConcurrency(int type, int concurrency) throws SQLException {
        return false;
    }

    public boolean ownUpdatesAreVisible(int type) throws SQLException {
        return false;
    }

    public boolean ownDeletesAreVisible(int type) throws SQLException {
        return false;
    }

    public boolean ownInsertsAreVisible(int type) throws SQLException {
        return false;
    }

    public boolean othersUpdatesAreVisible(int type) throws SQLException {
        return false;
    }

    public boolean othersDeletesAreVisible(int type) throws SQLException {
        return false;
    }

    public boolean othersInsertsAreVisible(int type) throws SQLException {
        return false;
    }

    public boolean updatesAreDetected(int type) throws SQLException {
        return false;
    }

    public boolean deletesAreDetected(int type) throws SQLException {
        return false;
    }

    public boolean insertsAreDetected(int type) throws SQLException {
        return false;
    }

    public boolean supportsBatchUpdates() throws SQLException {
        return false;
    }

    public ResultSet getUDTs(String catalog, String schemaPattern, String typeNamePattern, int[] types) throws SQLException {
        return null;
    }

    public Connection getConnection() throws SQLException {
        return null;
    }

    public boolean supportsSavepoints() throws SQLException {
        return false;
    }

    public boolean supportsNamedParameters() throws SQLException {
        return false;
    }

    public boolean supportsMultipleOpenResults() throws SQLException {
        return false;
    }

    public boolean supportsGetGeneratedKeys() throws SQLException {
        return false;
    }

    public ResultSet getSuperTypes(String catalog, String schemaPattern, String typeNamePattern) throws SQLException {
        return null;
    }

    public ResultSet getSuperTables(String catalog, String schemaPattern, String tableNamePattern) throws SQLException {
        return null;
    }

    public ResultSet getAttributes(String catalog, String schemaPattern, String typeNamePattern, String attributeNamePattern) throws SQLException {
        return null;
    }

    public boolean supportsResultSetHoldability(int holdability) throws SQLException {
        return false;
    }

    public int getResultSetHoldability() throws SQLException {
        return 0;
    }

    public int getDatabaseMajorVersion() throws SQLException {
        return 5; // todo: see: #getDatabaseProductVersion
    }

    public int getDatabaseMinorVersion() throws SQLException {
        return 0; // todo: see: #getDatabaseProductVersion
    }

    public int getJDBCMajorVersion() throws SQLException {
        return 4;
    }

    public int getJDBCMinorVersion() throws SQLException {
        return 0;
    }

    public int getSQLStateType() throws SQLException {
        return 0;
    }

    public boolean locatorsUpdateCopy() throws SQLException {
        return false;
    }

    public boolean supportsStatementPooling() throws SQLException {
        return false;
    }

    //@Override
    public RowIdLifetime getRowIdLifetime() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public ResultSet getSchemas(String string, String string1) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public ResultSet getClientInfoProperties() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public ResultSet getFunctions(String string, String string1, String string2) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public ResultSet getFunctionColumns(String string, String string1, String string2, String string3) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public ResultSet getPseudoColumns(String string, String string1, String string2, String string3) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public boolean generatedKeyAlwaysReturned() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public long getMaxLogicalLobSize() throws SQLException {
        return DatabaseMetaData.super.getMaxLogicalLobSize(); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public boolean supportsRefCursors() throws SQLException {
        return DatabaseMetaData.super.supportsRefCursors(); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public <T> T unwrap(Class<T> type) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public boolean isWrapperFor(Class<?> type) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
