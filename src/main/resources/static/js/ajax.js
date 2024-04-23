$(".submit-write button[type=submit]").click(addAnswer);

function addAnswer(e) {
    e.preventDefault();

    var queryString = $(".submit-write").serialize();
    var url = $(".submit-write").attr("action");


    $.ajax({
        type: 'post',
        url: url,
        data: queryString,
        dataType: 'json',
        error: function () {
            alert("error");
        },
        success: function (data, status) {
            console.log(data);
            var answerTemplate = $("#comment_template").html();
            var template = answerTemplate.format(data);

            $(".qna-comment-slipp-articles").append(template); // 추가
            $("textarea[name=content]").val("");  // 비운다
            refreshNumber();
        }
    });
}

$(document).on("click", "#delete-answer-form", deleteAnswer);

function deleteAnswer(e) {
    e.preventDefault();

    var deleteBtn = $(this);
    var url = $("#delete-answer-form").attr("href");

    $.ajax({
        type: 'delete',
        url: url,
        dataType: 'json',
        error: function (xhr, status) {
            console.log("error");
        },
        success: function (data, status) {
            console.log(data);
            if (data.valid) {
                deleteBtn.closest(".article").remove();
            }
            alert(data.message);

            refreshNumber();
        }
    });
}

function refreshNumber() {
    var number = document.getElementById("comments_block").querySelectorAll('article').length;
    document.getElementById("numberOfComments").innerText = number;
}
