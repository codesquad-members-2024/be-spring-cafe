String.prototype.format = function(arguments) {
  var args = arguments;
  return this.replace(/{(\w+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};
