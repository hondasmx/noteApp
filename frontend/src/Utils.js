export function formatDate(dateInMillis) {
    const options = {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
    };
    const date = new Date(dateInMillis);
    return date.toLocaleString("en-US", options);
}

export function isNumber(n) {
    return !isNaN(parseFloat(n)) && !isNaN(n - 0)
}