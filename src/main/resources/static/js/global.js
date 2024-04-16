window.onload = function() {
    const forms = document.getElementsByTagName('form');
    for (let i = 0; i < forms.length; i++) {
        forms[i].reset();
    }
}