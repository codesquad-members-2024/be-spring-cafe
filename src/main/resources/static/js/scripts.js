String.prototype.format = function () {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function (match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};

String.prototype.formatJson = function (json) {
  return this.replace(/{(\w+)}/g, function (match, key) {
    return typeof json[key] !== 'undefined' ? json[key] : match;
  });
};
