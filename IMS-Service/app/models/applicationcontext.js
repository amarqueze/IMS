module.exports = function(log) {
    var attributes = {};
    const handleLog = log;

    return {
        setAtrribute(key, value) {
            attributes[key] = value;
        },
        getAtrribute(key) {
            return attributes[key];
        },
        getLog() {
            return handleLog;
        }
    }
}
