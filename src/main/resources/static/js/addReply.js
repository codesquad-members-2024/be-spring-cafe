
$(".submit-write button[type=submit]").click(addReply);

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
    success : onSuccess
  });
}

function onError() {
  alert("error");
}

function onSuccess(data, status) {
  console.log(data);
  var replyTemplate = $("#replyTemplate").html();
  var template = replyTemplate.format(data.articleId, data.index, data.writer, data.content, data.formattedTimestamp);
  $(".qna-comment-slipp-articles").append(template)
  $("textarea[name=content]").val("");
  updateReplyCount(data.articleId);
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