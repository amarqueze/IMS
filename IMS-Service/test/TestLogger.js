var winston = require("winston");

var levels = {
    critical: 2,
    error: 3,
    warning: 4,
    notice: 5,
    info: 6,
    debug: 7
};

function Logger(context, filespath = []) {

    const { combine, timestamp, label, printf } = winston.format;

    const MessageFormat = printf(info => {
        return `${info.timestamp} [${info.label}] ${info.level}: ${info.message}`;
    });

    console.log(getTransport(filespath));
    var winstonLog = winston.createLogger({
        levels: levels,
        format: combine(
            label({ label: context }),
            timestamp(),
            MessageFormat
        ),
        transports: getTransport(filespath)
    });


    function getTransport(filespath) {
        var transports = [new winston.transports.Console()];
        var date = new Date();
        filespath.forEach((element) => {
            transports.push(new winston.transports.File({
                filename: `${element.filename}-${date.getFullYear()}-${date.getMonth()}-${date.getDay()}_${date.getHours()}-${date.getMinutes()}.log`,
                level: element.level
            })
            );
        })

        return transports;
    }

    return {
        log(message, level) {
            winstonLog.log(level, message);
        },
        critical(message) {
            winstonLog.log('critical', message);
        },
        error(message) {
            winstonLog.log('error', message);
        },
        warning(message) {
            winstonLog.log('warning', message);
        },
        notice(message) {
            winstonLog.log('notice', message);
        },
        info(message) {
            winstonLog.log('info', message);
        },
        debug(message) {
            winstonLog.log('debug', message);
        }
    }
}

var log = Logger("Node Server", [
    {filename: "error", level: "error"},
    {filename: "notice", level: "notice"},
    {filename: "server"}
]);

log.info("Server Start");
log.error("Server 404");
log.notice("Server Initializable");
