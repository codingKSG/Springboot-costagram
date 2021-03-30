

document.querySelector("#subscribeBtn").onclick = (e) => {
	e.preventDefault();
	document.querySelector(".modal-follow").style.display = "flex";

	//ajax 통신후에 json 리턴 -> javascript 오브젝트 변경 => for문 돌면서 뿌리기

	let userId = $("#userId").val();

	console.log(e);

	$.ajax({
		type: "GET",
		url: `/user/${userId}/follow`,
		dataType: "JSON"
	}).done((res) => {
		
		$("#follow_list").empty();
		
		res.data.forEach((u)=>{
			console.log(u);
			let item = makeSubScriveInfo(u.toUser.username, u.toUser.id);
			$("#follow_list").append(item);
		});
		
	}).fail((error)=>{
		alert("오류: ",+error);
	});
};

function makeSubScriveInfo(username, userId) {
	let item = `<div class="follower__item">`;
	item += `<div class="follower__img">`;
	item += `<img src="/images/profile.jpeg" alt=""/>`;
	item += `</div>`;
	item += `<div class="follower__text">`;
	item += `<a href="/user/${userId}" class="follower__item__href">`;
	item += `<h2>${username}</h2></a>`;
	item += `</div>`;
	item += `<div class="follower__btn">`;
	item += `<button onclick = "clickFollow(this)"> 구독취소</button>`;
	item += `</div></div>`;

	return item;
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

function clickFollow(e) {
	console.log(e);
	let _btn = e;
	console.log(_btn.textContent);
	if (_btn.textContent === "구독취소") {
		_btn.textContent = "구독하기";
		_btn.style.backgroundColor = "#fff";
		_btn.style.color = "#000";
		_btn.style.border = "1px solid #ddd";
	} else {
		_btn.textContent = "구독취소";
		_btn.style.backgroundColor = "#0095f6";
		_btn.style.color = "#fff";
		_btn.style.border = "0";
	}
}
