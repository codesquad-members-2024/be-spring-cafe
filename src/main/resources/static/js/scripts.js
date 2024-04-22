// 페이지 로드 시 실행되는 함수
$(document).ready(function() {
  // 페이지 로드 시 댓글을 가져오도록 호출
  fetchComments();

  // 댓글 작성 버튼 클릭 시
  $(".submit-write button[type=submit]").click(addAnswer);

  // 삭제 버튼 클릭 시
  $(document).on("click", ".delete-answer-button", function(event) {
    event.preventDefault(); // 기본 이벤트 제거

    // 해당 댓글의 articleId와 commentId 가져오기
    const articleId = $(this).closest(".delete-answer-form").data("article-id");
    const commentId = $(this).data("id");

    // 댓글 삭제 함수 호출
    deleteComment(articleId, commentId);
  });
});

// 서버로부터 댓글 리스트를 가져오는 함수
function fetchComments() {
  var url = $(".submit-write").attr("action");

  $.ajax({
    type : 'get',
    url : url,
    dataType : 'json',
    error: function () {
      console.log('failure');
    },
    success : function (comments, status) {
      displayComments(comments);
      $("textarea#content")[0].value = '' // 텍스트 비우기
      console.log(comments);
    }
  });
}

// 서버에 작성한 댓글을 전송하는 함수
function addAnswer(e) {
  e.preventDefault(); //submit 이 자동으로 동작하는 것을 막는다.

  const formData = {
    content: $(".submit-write textarea[name=content]").val() // 클라이언트에서 전송할 content 필드의 값, 이 예제에서는 비어있음
  };
  console.log("formData : " + formData);

  const url = $(".submit-write").attr("action");
  console.log("url : " + url);

  $.ajax({
    type : 'post',
    contentType : 'application/json',
    url : url,
    data : JSON.stringify(formData),
    dataType : 'json',
    error: function (xhr) {
      if (xhr.status === 400) {
        alert("빈 내용을 등록할 수 없습니다. 1글자 이상 작성해주세요.")
      }
    },
    success : function (comments, status) {
      displayComments(comments);
      $("textarea#content")[0].value = '' // 텍스트 비우기
      console.log(comments);
    }
  });
}

// 댓글 표시 함수
function displayComments(comments) {
  const commentList = document.getElementById('comment-list');
  const commentCount = $('.qna-comment-count').find('strong');
  commentCount.eq(0).text(comments.length); // 댓글 수 업데이트
  commentList.innerHTML = ''; // 기존 댓글 삭제
  comments.forEach(comment => {
    const article = document.createElement('article');
    article.innerHTML = addComment(comment); <!-- 댓글 내용, 작성자 정보 등 표시 -->
    commentList.appendChild(article);
  });

  // 댓글 작성자에 따라 버튼 보이기/숨기기
  hideButton();
}


// 댓글 HTML 생성 함수
function addComment(content) {
  const formattedContent = content.content.replace(/\n/g, '<br>');
  return `
    <div class="article-header">
      <div class="article-header-thumb">
        <img src="https://graph.facebook.com/v2.3/1324855987/picture"
             class="article-author-thumb" alt="">
      </div>
      <div class="article-header-text" data-user-id="${content.createdBy}">
        <a href="/members/${content.createdBy}" class="article-author-name">
          <span>${content.createdBy}</span>
        </a>
        <div class="article-header-time">
          <span>${formatLocalDateTime(content.createdAt)}</span>
          <i class="icon-link"></i>
        </div>
      </div>
    </div>
    <div class="article-doc comment-doc">
      <p>${formattedContent}</p>
    </div>
    <div class="article-util">
      <ul class="article-util-list" data-user-id="${content.createdBy}">
        <li>
          <a class="link-modify-article" href="/questions/${content.articleId}/comments/${content.id}/edit">
            수정
          </a>
        </li>
        <li>
          <form class="delete-answer-form" action="/api/questions/${content.articleId}/comments/${content.id}"
          method="post" data-article-id="${content.articleId}">
            <input type="hidden" name="_method" value="delete" />
            <button type="submit" class="delete-answer-button" data-id="${content.id}">삭제</button>
          </form>
        </li>
      </ul>
    </div>
  `;
}

// LocalDateTime 형식의 시간을 포맷팅하는 함수
function formatLocalDateTime(localDateTimeString) {
  const localDateTime = new Date(localDateTimeString);

  const year = localDateTime.getFullYear();
  const month = String(localDateTime.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 +1
  const day = String(localDateTime.getDate()).padStart(2, '0');
  const hours = String(localDateTime.getHours()).padStart(2, '0');
  const minutes = String(localDateTime.getMinutes()).padStart(2, '0');
  const seconds = String(localDateTime.getSeconds()).padStart(2, '0');

  return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
}

// 댓글 삭제 함수
function deleteComment(articleId, commentId) {
  const url = "/api/questions/" + articleId + "/comments/" + commentId;
  console.log("url : " + url);

  $.ajax({
    type : 'delete',
    contentType: 'application/json',
    url : url,
    dataType : 'json',
    error: function (xhr) {
      if (xhr.status === 403) {
        alert("다른 회원의 댓글을 삭제할 권한이 없습니다.");
      } else {
        console.log('failure');
      }
    },
    success : function (comments, status) {
      displayComments(comments);
      console.log(comments);
    }
  });
}

// 현재 로그인한 사용자의 아이디를 받아오고, 댓글 작성자에 따라 버튼 보이기/숨기기
function hideButton() {
  $.ajax({
    type: 'get',
    url: '/api/currentUser',
    success: function (response) {
      const currentUser = response.username; // 현재 로그인한 사용자의 아이디
      console.log(currentUser);
      // 각 댓글에 대한 작성자 아이디와 비교하여 수정과 삭제 버튼을 보이거나 숨김
      $(".article-util-list").each(function () {
        const commentUserId = $(this).data("user-id");
        console.log("commentId: ", commentUserId);
        if (currentUser === commentUserId) {
          $(this).find(".link-modify-article").show();
          $(this).find(".delete-answer-button").show();
        } else {
          $(this).empty();
        }
      });
    },
    error: function () {
      console.log('Error fetching current user information');
    }
  });
}
