// 회원 정보 수정
function update(userId) {
	let data = $("#profile_setting").serialize();

	$.ajax({
		type: "PUT",
		url: "/user/" + userId,
		data: data,
		contentType: "application/x-www-form-urlencoded; charset=utf-8"
	}).done((res) => {
		if (res.statusCode === 1) {
			location.href = 'http://localhost:8080/user/' + userId;
		}
	});
}