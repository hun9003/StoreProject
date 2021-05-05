/**
 *  사용자 회원가입 유효성 검사
 */
$(document).ready(function () {
    const isSuccessID = $('#dupid');
    const isSuccessName = $('#dupname');
    const memberPhone = $('#mem_phone');
    $(document).on('change', '#mem_userid', function () {
        isSuccessID.val('0');
    });
    $(document).on('change', '#mem_nickname', function () {
        isSuccessName.val('0');
    });

    $(document).on('keyup', '#mem_phone', function () {
        const number = memberPhone.val().replace(/[^0-9]/g, "");
        let phone = "";
        if (number.length < 4) {
            return number;
        } else if (number.length < 7) {
            phone += number.substr(0, 3);
            phone += "-";
            phone += number.substr(3);
        } else if (number.length < 11) {
            phone += number.substr(0, 3);
            phone += "-";
            phone += number.substr(3, 3);
            phone += "-";
            phone += number.substr(6);
        } else {
            phone += number.substr(0, 3);
            phone += "-";
            phone += number.substr(3, 4);
            phone += "-";
            phone += number.substr(7);
        }
        memberPhone.val(phone);


    })
})

function checkDupId() {
    const isSuccess = $('#dupid');
    const userid = $('#mem_userid').val();
    $.ajax('ckUserid', {
        data: {mem_userid: userid},
        success: function (data) {
            if (data == 'true') {
                alert('사용 가능한 아이디 입니다.');
                isSuccess.val('1');
            } else {
                alert('이미 사용중인 아이디 입니다.');
                isSuccess.val('0');
            }
        }
    })
}

function checkDupName() {
    const isSuccess = $('#dupname');
    const nickname = $('#mem_userid').val();
    $.ajax('ckUserid', {
        data: {mem_nickname: nickname},
        success: function (data) {
            if (data == 'true') {
                alert('사용 가능한 닉네임 입니다.');
                isSuccess.val('1');
            } else {
                alert('이미 사용중인 닉네임 입니다.');
                isSuccess.val('0');
            }
        }
    })
}

function join() { // 회원가입
    const msg = $('#alertMsg');
    const userid = $('#mem_userid').val();
    const password = $('#mem_password').val();
    const password2 = $('#mem_password2').val();
    const nickname = $('#mem_nickname').val();
    const email = $('#mem_email').val();
    const phone = $('#mem_phone').val();
    const dupid = $('#dupid').val();
    const dupname = $('#dupname').val();

    const idRule = /^[A-Za-z0-9]{6,12}$/;
    const passwordRule = /^.*(?=^.{8,30}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/;
    const nameRule = /[a-zA-Z0-9ㄱ-ㅎ|ㅏ-ㅣ|가-힣]+/;
    const emailRule = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;//이메일 정규식
    const mobileRule = /^\d{3}-\d{3,4}-\d{4}$/;//전화번호 정규식


    // 닉네임 한글 포함 길이 파악
    let len = 0;
    for (let i = 0; i < nickname.length; i++) {
        if (escape(nickname.charAt(i)).length === 6) {
            len++;
        }
        len++;
    }

    if (!idRule.test(userid)) {
        msg.html('아이디가 적절치 않습니다. (영문과 숫자로 이루어진 6~12자)');
        $('#mem_userid').focus();
        return false;
    } else if (!passwordRule.test(password)) {
        msg.html('비밀번호가 적절치 않습니다. (영문,숫자,특수문자로 이루어진 8~30자)');
        $('#mem_password').focus();
        return false;
    } else if (password !== password2) {
        msg.html('비밀번호와 일치하지 않습니다');
        $('#mem_password2').focus();
        return false;
    } else if (len < 4 && len > 12 && !nameRule.test(nickname)) {
        msg.html('닉네임이 적절치 않습니다 (4~12글자로 입력하세요 한글은 2글자 취급하며 특수문자는 사용할 수 없습니다)');
        $('#mem_nickname').focus();
        return false;
    } else if (!emailRule.test(email)) {
        msg.html('이메일이 적절치 않습니다.');
        $('#mem_email').focus();
        return false;
    } else if (!mobileRule.test(phone)) {
        msg.html('전화번호가 적절치 않습니다.');
        $('#mem_phone').focus();
        return false;
    } else if (dupid != 1) {
        msg.html('아이디 중복체크를 해주세요');
        $('#mem_userid').focus();
        return false;
    } else if (dupname != 1) {
        msg.html('닉네임 중복체크를 해주세요');
        $('#mem_nickname').focus();
        return false;
    } else {
        return true;
    }


}