import React from "react";

export function formatDate(dateInMillis) {
    const options = {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
    };
    const date = new Date(dateInMillis);
    return date.toLocaleString("en-US", options);
}

export function errorToast() {
    return (
        <div className="toast"
             role="alert"
             aria-live="assertive"
             aria-atomic="true">
            <div className="toast-header">
                <strong className="mr-auto">Error</strong>
                <button type="button"
                        className="ml-2 mb-1 close"
                        data-dismiss="toast"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div className="toast-body">
                Cannot update or save the note with this parameters.
            </div>
        </div>
    )
}