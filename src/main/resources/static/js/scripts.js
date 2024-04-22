String.prototype.format = function(arguments) {
  var args = arguments;
  return this.replace(/{(\w+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};

// String.prototype.format = function(data) {
//     return this.replace(/{(\w+)}/g, function(match, key) {
//         return typeof data[key] !== 'undefined' ? data[key] : match;
//     });
// };
