$("#article-page-block").on("click", "li a", nextArticlePage);

function nextArticlePage(e) {
    e.preventDefault();

    var pageNumber = $(this).text();
    var url = "/api/article?page=" + pageNumber;


    $.ajax({
        type: 'get',
        url: url,
        dataType: 'json',
        error: function () {
            alert("error");
        },
        success: function (data, status) {
            console.log(data);
            $("#article-list").empty();
            data.forEach(function (item) {
                var answerTemplate = $("#article-list-template").html();
                var template = answerTemplate.format(item)
                    .format({
                        'author_id': item.author.id,
                        'author_name': item.author.name
                    });
                $("#article-list").append(template); // 추가
            })
        }
    });
}