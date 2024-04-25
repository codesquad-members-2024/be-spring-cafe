$(document).ready(function () {
    $(".post-write-btn").click(addAnswer);
});

function addAnswer(e) {
    e.preventDefault();

    var replyData = {
        writer: $("#writer").val(),
        comment: $("#comment").val()
    };

    var url = $(".submit-write").attr("action");

    $.ajax({
        url: url,
        type: 'post',
        data: JSON.stringify(replyData),
        dataType: 'json',
        contentType: 'application/json',
        success: function (reply) {
            var answerTemplate = $("#comment-template").html();
            var template = answerTemplate.format(reply);

            $(".post-comment-slipp-articles").append(template);
            $("textarea[name=comment]").val("");

            var divElement = document.querySelector('.post-comment-slipp-articles');

            var articleCount = divElement.querySelectorAll('article').length - 1;

            var pElement = document.querySelector('.post-comment-count');

            pElement.textContent = "댓글 " + articleCount + "개";
        },
        error: function () {
            alert("error");
        }
    });
}

String.prototype.format = function (arguments) {
    var args = arguments;
    return this.replace(/{(\w+)}/g, function (match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};

