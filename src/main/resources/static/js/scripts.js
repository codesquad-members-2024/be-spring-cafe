String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};

$(".delete-answer-form button[type='submit']").click(deleteAnswer);

function deleteAnswer(e) {
  e.preventDefault();

  var deleteBtn = $(this);
  var url = deleteBtn.closest(".delete-answer-form").attr("action");

  $.ajax({
    type : 'delete',
    url : url,
    dataType : 'json',
    error : function (xhr, status) {
      console.alert('error');
    },
    success : function (data, status) {
      console.log(data);
      if (data.valid) {
        deleteBtn.closest("article").remove();
      } else {
        alert(data.errorMessage);
      }
    }
  });
}