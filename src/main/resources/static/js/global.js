window.addEventListener('pageshow', function (event) {
    if (event.persisted || (window.performance && performance.getEntriesByType("navigation")[0].entryType === 'back_forward')) {
        location.reload();
    }
});