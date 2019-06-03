var sel_files =[];

$(document).ready(function(){
	$("#input_images").on("change", ImgsFilesSelect);
});

function ImgsFilesSelect(e){
	var files = e.target.files;
	var filesArr = Array.prototype.slice.call(files);
	
	filesArr.forEach(function(f){
		if(!f.type.match("image.*")){
			alert("확자자는 이미지 확장자만 가능합니다.");
			return;
		}
		
		sel_files.push(f);
		
		var reader = new FileReader();
		reader.onload = function(e){
			var img_html = "<img src=\"" + e.target.result +"\" />";
			$(".upload_images").append(img_html);
		}
		reader.readAsDataURL(f);
	});
}