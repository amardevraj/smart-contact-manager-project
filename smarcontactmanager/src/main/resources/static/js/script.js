console.log("base js code");

const sidebarToggle=()=>{
    if($(".sidebar").is(":visible")){
        $(".sidebar").css("display","none");
        $(".content").css("margin-left","0%");
    }else{
        $(".sidebar").css("display","block");
        $(".content").css("margin-left","20%");
        $(".fas fa-bars.top-menu-btn").css("left","40px");
    }
}