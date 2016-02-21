import grails.util.BuildSettings
import grails.util.Environment
import com.logentries.logback.LogentriesAppender

// See http://logback.qos.ch/manual/groovy.html for details on configuration
appender('STDOUT', ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%level %logger - %msg%n"
    }
}

if (Environment.getCurrent() == Environment.PRODUCTION) {
    appender('LOGENTRIES', LogentriesAppender) {
        encoder(PatternLayoutEncoder) {
            pattern = '%d{yyyy-MM-dd HH:mm:ss} %-5p [%-18c{1}] %m%n'
        }
        ssl = false
        facility = 'USER'
        layout(ch.qos.logback.classic.PatternLayout) {
            pattern = '%d{yyyy-MM-dd HH:mm:ss} %-5p [%-18c{1}] %m%n'
        }
        token = "d501da46-62a3-4a06-93f7-c76733c4b082"
    }
    root(ERROR, ['STDOUT', 'LOGENTRIES'])
    root(WARN, ['LOGENTRIES'])
    root(INFO, ['LOGENTRIES'])
    logger("StackTrace", ERROR, ['LOGENTRIES'], false)
    logger("StackTrace", INFO, ['LOGENTRIES'], false)
    logger("StackTrace", WARN , ['LOGENTRIES'], false)
} else {
    root(ERROR, ['STDOUT'])
}

def targetDir = BuildSettings.TARGET_DIR
if (Environment.isDevelopmentMode() && targetDir) {
    appender("FULL_STACKTRACE", FileAppender) {
        file = "${targetDir}/stacktrace.log"
        append = true
        encoder(PatternLayoutEncoder) {
            pattern = "%level %logger - %msg%n"
        }
    }
    logger("StackTrace", ERROR, ['FULL_STACKTRACE'], false)
}
