/**
 * Created by admin on 2016/12/18.
 */
$(function () {
    $('#tagId').chosen()
});

function add() {
    var data = {
        title: $('#title').val(),
        id: $('#id').val(),
        mid: $('#mid').val(),
        toall: $('#toall').is(":checked"),
        accountId: $('#accountId').val(),
        tagId: $('#tagId').val(),
        remark: $('#remark').val(),
        domain: $.cookie('d'),
        openId: ''
    };
    if (data.title == '') {
        $.showToast('标题不能为空')
        return
    }
    if (data.tagId == '') {
        $.showToast('标签不能为空')
        return
    }
    $.ajax({
        url: 'addMessageRecord',
        type: "post",
        data: data,
        dataType: 'json',
        success: function (resp) {
            if (resp.success) {
                $.showToast('保存成功', function () {
                    window.location.href = "index.html";
                })
            } else {
                $.showToast(resp.info);
            }
        },
        error: function (resp) {
            $.showToast('系统出错')
        }
    });
}

function cancel() {
    history.back();
}
