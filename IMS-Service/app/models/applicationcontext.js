module.exports = function(session, log) {
    var attributes = {};
    const handleSession = session;
    const handleLog = log;

    return {
        setAtrribute(key, value) {
            attributes[key] = value;
        },
        getAtrribute(key) {
            return attributes[key];
        },
        setSession(token, value) {

        },
        getSession(token) {

        },
        getLog() {
            return handleLog;
        }
    }
}
