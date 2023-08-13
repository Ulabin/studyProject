var signup = {
    init : function () {
        var _this = this;
        document.getElementById("btnSubmit").onclick = function (event) {
            _this.submit(event);
        };
        document.getElementById("profileImgInput").onchange = function (event) {
            _this.upload(event);
        }
    },
    submit : function (event) {
        event.preventDefault();

        var name = document.getElementById("name");
        var id = document.getElementById("id");
        var pw = document.getElementById("pw");
        var confPw = document.getElementById("confPw");
        var signupForm = document.getElementById("signupForm");

        if(name.value==""){
            alert("Please input name");
            name.focus();
            return;
        }else if(id.value==""){
            alert("Please input id");
            id.focus();
            return;
        }else if(pw.value==""){
            alert("Please input pw");
            pw.focus();
            return;
        }else if(pw.value !== confPw.value){
            alert("Please retype password and confirm password");
            confPw.value="";
            pw.value="";
            pw.focus();
            return;
        }else{
            signupForm.submit();
        }
    },
    upload : async function (event) {
        var _this= this;
        var uploadFile = event.target.files[0];

        const formData = new FormData();
        formData.append("uploadFile", uploadFile);

        await axios({
            method:'post',
            url:'/files',
            data: formData,
            headers:{
                'Content-Type': 'multipart/form-data',
            },
        })
            .then(function ({data}) {
                console.log(data);
                _this.showUploadedImages(data);
            })
            .catch(function (error) {
                console.log(error);
            })
    },
    showUploadedImages : function (data) {
        var _this=this;
        var img = document.getElementById("profileImg");
        //console.log(img);
        var imgUrl = data.imgUrl
        img.src = "/file?fileName="+ imgUrl;
        _this.inputImagePath(img.src);
    },
    inputImagePath : function (imgUrl) {
        var input = document.getElementById("profilePath");
        input.value = imgUrl;
    }
}

signup.init();