/**
 * 使用iframe下载文件
 * @param url
 * @param params
 */
function downloadFile(url,params) {
    if(url){
        if(!$.isEmptyObject(params)){
            url += "?";
            let index = 0;
            for (let key in params){
                if(index++ != 0){
                    url += "&";
                }
                url += key+"="+params[key];
            }
        }
        let iframe = document.getElementById("downloadIframe");
        if(iframe){
            iframe.src = url;
        }else{
            iframe = document.createElement("iframe");
            iframe.id = "downloadIframe";
            iframe.style.display = "none";
            iframe.src = url;
            document.body.appendChild(iframe);
        }
    }
}