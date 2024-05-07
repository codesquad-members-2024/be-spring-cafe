String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};

$(".submit-write button[type=submit]").click(addReply);
$(".qna-comment-slipp-articles").on("click", ".delete-answer-form button[type='submit']", deleteReply);

function addReply(e) {
  e.preventDefault(); //submit 이 자동으로 동작하는 것을 막는다.

  var queryString = $(".submit-write").serialize();
  console.log("query : "+ queryString);
  var url = $(".submit-write").attr("action");
  console.log("url : " + url);

  $.ajax({
    type: 'post',
    url: url,
    data: queryString,
    dataType: 'json',
    error: onError,
    success : onAddReplySuccess
  });
}

function deleteReply(e) {
  e.preventDefault();

  var deleteBtn = $(this);
  var url = deleteBtn.closest(".delete-answer-form").attr("action");
  var articleId = parseInt(url.split("/").slice(-3, -2)[0]);
  console.log(articleId);

  $.ajax({
    type : 'delete',
    url : url,
    dataType : 'json',
    error: onError,
    success : function(data, status) {
      console.log(data);
      if (data.valid) {
        deleteBtn.closest("article").remove();
        updateReplyCount(articleId);
        return;
      }
      alert(data.errorMessage);
    }
  });
}

function onAddReplySuccess(data, status) {
  console.log(data);
  var replyTemplate = $("#replyTemplate").html();
  var template = replyTemplate.format(data.articleId, data.index, data.writer, data.content, data.formattedTimestamp);
  $(".qna-comment-slipp-articles").append(template)
  $("textarea[name=content]").val("");
  updateReplyCount(data.articleId);
}

function onError() {
  alert("error");
}

function updateReplyCount(articleId) {
  $.ajax({
    type: 'get',
    url: '/api/articles/' + articleId + '/replies/count',
    dataType: 'json',
    error: onError,
    success: function(response) {
      // 서버로부터의 응답에서 새로운 댓글 수를 가져옵니다.
      var newCommentCount = response;
      $("#numberOfReplies strong").text(newCommentCount);
    }
  });
}

