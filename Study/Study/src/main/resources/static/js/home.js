window.onload=function(){
    var lis=document.getElementsByClassName("MZ_H_body_main_info_ul_li")
    for(var i=0;i<lis.length;i++){
        lis[i].onclick=function(){
            lis[0].style.backgroundColor="#002f5d";
            lis[1].style.backgroundColor="#002f5d";
            lis[2].style.backgroundColor="#002f5d";
            lis[3].style.backgroundColor="#002f5d";
            lis[4].style.backgroundColor="#002f5d";
            lis[5].style.backgroundColor="#002f5d";
            lis[6].style.backgroundColor="#002f5d";
            this.style.backgroundColor="#5c809d";
        }

    }
}