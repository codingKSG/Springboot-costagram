// 구독자 정보  모달 보기
$("#subscribe_btn").on("click", (e) => {
	e.preventDefault();
	$(".modal-follow").css("display", "flex");
	
	//ajax 통신후에 json 리턴 -> javascript 오브젝트 변경 => for문 돌면서 뿌리기

	let userId = $("#userId").val();

	$.ajax({
		url: `/user/${userId}/follow`,
	}).done((res) => {
		$("#follow_list").empty();
		res.data.forEach((u) => {
			console.log(u);
			let item = makeSubScriveInfo(u);
			$("#follow_list").append(item);
		});

	}).fail((error) => {
		alert("오류: ", +error);
	});
});

function makeSubScriveInfo(u) {
	let item = `<div class="follower__item" id="follow-${u.userId}">`;
	item += `<div class="follower__img">`;
	item += `<img src="/upload/${u.profileImageUrl}" alt=""  onerror="this.src='/images/person.jpg'"/>`;
	item += `</div>`;
	item += `<div class="follower__text">`;
	item += `<a href="/user/${u.userId}" class="follower__item__href">`;
	item += `<h2>${u.username}</h2></a>`;
	item += `</div>`;
	item += `<div class="follower__btn">`;
	if (!u.equalState) {
		if (u.followState) {
			item += `<button class="cta blue" onclick="followOrUnFollowModal(${u.userId})">구독취소</button>`;
		} else {
			item += `<button class="cta" onclick="followOrUnFollowModal(${u.userId})">구독하기</button>`;
		}
	}
	item += `</div></div>`;

	return item;
}

function followOrUnFollowModal(userId) {
	let text = $(`#follow-${userId} button`).text();

	if (text === "구독취소") {
		$.ajax({
			type: "DELETE",
			url: "/follow/" + userId,
			dataType: "json"
		}).done(res => {
			$(`#follow-${userId} button`).text("구독하기");
			$(`#follow-${userId} button`).toggleClass("blue");
		});
	} else {
		$.ajax({
			type: "POST",
			url: "/follow/" + userId,
			dataType: "json"
		}).done(res => {
			$(`#follow-${userId} button`).text("구독취소");
			$(`#follow-${userId} button`).toggleClass("blue");
		});
	}
}

function followOrUnFollowProfile(userId) {
	let text = $(`#follow_profile_btn`).text();

	if (text === "구독취소") {
		$.ajax({
			type: "DELETE",
			url: "/follow/" + userId,
			dataType: "json"
		}).done(res => {
			$(`#follow_profile_btn`).text("구독하기");
			$(`#follow_profile_btn`).toggleClass("blue");
		});
	} else {
		$.ajax({
			type: "POST",
			url: "/follow/" + userId,
			dataType: "json"
		}).done(res => {
			$(`#follow_profile_btn`).text("구독취소");
			$(`#follow_profile_btn`).toggleClass("blue");
		});
	}
}

function closeFollow() {
	document.querySelector(".modal-follow").style.display = "none";
}
document.querySelector(".modal-follow").addEventListener("click", (e) => {
	if (e.target.tagName !== "BUTTON") {
		document.querySelector(".modal-follow").style.display = "none";
	}
});

function popup(obj) {
	console.log(obj);
	document.querySelector(obj).style.display = "flex";
}
function closePopup(obj) {
	console.log(2);
	document.querySelector(obj).style.display = "none";
}
document.querySelector(".modal-info").addEventListener("click", (e) => {
	if (e.target.tagName === "DIV") {
		document.querySelector(".modal-info").style.display = "none";
	}
});

document.querySelector(".modal-image").addEventListener("click", (e) => {
	if (e.target.tagName === "DIV") {
		document.querySelector(".modal-image").style.display = "none";
	}
});

/*function clickFollow(e) {
	console.log(e);
	let _btn = e;
	console.log(_btn.textContent);
	if (_btn.textContent === "구독취소") {
		_btn.textContent = "구독하기";
		document.getElementById('followBtn').className = 'cta';
		
	} else {
		_btn.textContent = "구독취소";
		document.getElementById('followBtn').className = 'cta blue';
		
	}
}*/

function profileImageUpload(){

      let principalId = $("#principal-id").val();
      
      $("#profile-image_input").click();

      $("#profile-image_input").on("change", (e)=>{
         let files = e.target.files;
         let filesArr = Array.prototype.slice.call(files);
         filesArr.forEach((f)=>{
            if(!f.type.match("image.*")){
               alert("이미지를 등록해야 합니다.");
               return;
            }

            // 통신 시작
            let profileImageForm = $("#profile-image_form")[0];
            
            let data = new FormData(profileImageForm); // Form태그 데이터 전송 타입을 multipart/form-data 로 만들어줌.

            $.ajax({
               type: "put",
               url: "/user/"+principalId+"/profileImageUrl",
               data: data,
               contentType: false, //필수
               processData: false, //필수 : contentType을 false로 줬을 때 쿼리 스트링으로 자동 설정됨. 그거 해제 하는 법
               encType: "multipart/form-data", // 필수 아님(보내는 data가 FormData가 아닐 경우 필수)
               dataType: "json"
            }).done(res=>{

               // 사진 전송 성공시 이미지 변경
               let reader = new FileReader();
               reader.onload = (e) => { 
                  $("#profile-image-url").attr("src", e.target.result);
               }
               reader.readAsDataURL(f); // 이 코드 실행시 reader.onload 실행됨.
            });

         });
      });
   }