package io.api.carrent.infra.repositories.queries.sql;

public class SqlConstants {
    public static String WHERE_VAR = ":where";
    public static String OFFSET_VAR = ":offset";
    public static String OFFSET_SQL = "OFFSET ((:page - 1) * :perPage) ROWS FETCH NEXT :perPage ROWS ONLY";
}
