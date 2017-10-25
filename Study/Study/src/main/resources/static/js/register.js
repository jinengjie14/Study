window.onload=function(){
    var reg ={
        num:/^20\d{7,13}$/,
        user:/^[\u4e00-\u9fa5]{2,4}$/,
        mail:(/^([a-z0-9_\.\-])+\@(([a-z0-9\-])+\.)+([a-z0-9]{2,4})+$/i),
        pass:(/^[0-9a-zA-Z\_]{6,15}$/),
    }
    var num= document.getElementById("MZ_R_regis_num");
    var user=document.getElementById("MZ_R_regis_user");
    var mail=document.getElementById("MZ_R_regis_mail");
    var pass=document.getElementById("MZ_R_regis_pass");
    var qpass=document.getElementById("MZ_R_regis_qpass");
    num.onkeyup=function() {
        !reg.num.test(this.value)?this.style.borderColor="red":this.style.borderColor= "green";
    }
    user.onkeyup=function(){
        !reg.user.test(this.value)?this.style.borderColor="red":this.style.borderColor= "green";
    }
    mail.onkeyup=function(){
        !reg.mail.test(this.value)?this.style.borderColor="red":this.style.borderColor= "green";
    }
    pass.onkeyup=function(){
        !reg.pass.test(this.value)?this.style.borderColor="red":this.style.borderColor= "green";
    }
    qpass.onkeyup=function(){
        if(qpass.value==pass.value){
            this.style.borderColor="green"
        }else{
            this.style.borderColor= "red"
        }
    }
}
