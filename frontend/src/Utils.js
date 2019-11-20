export function formatDate(dateInMillis) {
    const options = {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
    };
    const date = new Date(dateInMillis);
    return date.toLocaleString("en-US", options);
}