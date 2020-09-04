/**
 * 使用正则格式化时间
 * @param date
 * @param pattern
 * @returns {string|*}
 */
function formatDateByPattern(date,pattern) {
    if(date && pattern){
        return new XDate(date).toString(pattern);
    }
    return '';
}