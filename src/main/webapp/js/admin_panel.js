$(function(){
    var id=this.id.match('[0-9]+');
    $("#id").bind("click",function(){
        $("#update").submit();
    });
});
