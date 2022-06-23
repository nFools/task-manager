var fileInput = document.getElementById("file-input")

var dataTransferFiles
let contextPath = "/task-manager"
var files = []
function submit() {
    $('#start').prop("disabled", true)
    $('#tips').innerHTML = "load..."
    const formData = new FormData()
    for (let i = 0; i < files.length; i++) {
        formData.append("files", files[i])
    }
    var taskName = $("#task-name").val()
    var remarks = $("#remarks").val()
    var parameter = {
        "taskName": taskName,
        "remarks": remarks
    }
    formData.append("parameterString", JSON.stringify(parameter))
    $.ajax({
        url: contextPath + "/api/create-task",
        type: "post",
        contentType: false,
        cache: false,
        data: formData,
        processData: false,
        success: function (result, status, xhr) {
            console.log(JSON.stringify(result))
            $('#tips').innerHTML = "ok！"
            console.log('complete')
            $('#task-name').val('')
            $('#remarks').val('')
            $('#file-input').html('<p>Put Files Here</p>')
            $('#exampleModal').modal('hide')
            refresh()
            files = []
            $('#start').prop("disabled", false)
        },
        error: function (xhr,status,error) {
            console.log(xhr)
            console.log(error)
            $('#tips').html(xhr.responseText)

        },
        complete: function () {

        }
    });

}

fileInput.addEventListener("dragover", function (e) {
    e.preventDefault();
}, false);
fileInput.addEventListener("drop", function (e) {
    e.preventDefault();
    dataTransferFiles = e.dataTransfer.files;
    [].forEach.call(dataTransferFiles, function (file) {
        files.push(file);
    }, false);
    let message = ""
    for (let i = 0; i < files.length; i++) {
        message += "[" + files[i].name + "]"
        console.log("filename " + files[i].name)
    }
    fileInput.innerHTML = message
}, false)

window.addEventListener("keydown", function (e) {
    if (!e.shiftKey && 'Delete' === e.key) {
        e.preventDefault();
        if ("file-input" === document.activeElement.id) {
            files.pop()
            let message = ""
            for (let i = 0; i < files.length; i++) {
                message += "[" + dataTransferFiles[i].name + "]"
                console.log("filename " + files[i].name)
            }
            fileInput.innerHTML = message
        }
    }
}, false);

$("#refresh").click(refresh)

function refresh() {
    $.get({
        url: contextPath + "/ui/refresh",
        success: function (result, status, xhr) {
            $('#task-div').html(result)
        },
        error: function () {

        },
        complete: function () {
        }
    });
}
function deleteTask(id) {
    console.log("delete " + id)
    $.ajax({
        url: contextPath + "/api/delete-task/" + id,
        type: "delete",
        success: function (result, status, xhr) {
            console.log(result)
        },
        error: function () {

        },
        complete: function () {
            refresh()
        }
    });
}