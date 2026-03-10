import net.sf.jsqlparser.JSQLParserException
import net.sf.jsqlparser.parser.CCJSqlParserManager
import java.io.StringReader

// Function that validates SQL 
fun isSqlQueryValid(sql: String): Boolean {
    
    // Check if SQL is empty
    if (sql.trim().isEmpty()) {
        return false
    }

    // List of valid SQL commands
    val pattern = Regex(
        "\\b(SELECT|INSERT|UPDATE|DELETE|WITH|CREATE|DROP|SHOW|DESCRIBE)\\b",
        RegexOption.IGNORE_CASE
    )

    if (!pattern.containsMatchIn(sql)) {
        return false
    }

    return try {
        // Try formatting/parsing to catch syntax error
        val parser = CCJSqlParserManager()
        parser.parse(StringReader(sql))
    } catch (e: JSQLParserException) {
        false
    }
}