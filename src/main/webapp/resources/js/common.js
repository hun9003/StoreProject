// 공통 자바 스크립트
function paging(url) {
    location.href = url;
}

function pass_show_toggle() {
    let is_check = document.getElementById('pass_show');
    let password = document.getElementById("mem_password");
    if (is_check.checked) {
        password.type = 'text';
    } else {
        password.type = 'password';
    }

}