$(function () {

    //收缩菜单
    $('.gp').each(function(index,el){
        var loc = $(this);
        var val = loc.attr('val');
        if (loc.hasClass('glyphicon-plus')) {
            loc.removeClass('glyphicon-plus');
            loc.addClass('glyphicon-minus');
            $('tr[pid=' + val + ']').show();
        } else {
            loc.removeClass('glyphicon-minus');
            loc.addClass('glyphicon-plus');
            $('tr[pid=' + val + ']').hide();
        }
    });

    $('.gp').click(function () {
        var loc = $(this);
        var val = loc.attr('val');
        if (loc.hasClass('glyphicon-plus')) {
            loc.removeClass('glyphicon-plus');
            loc.addClass('glyphicon-minus');
            $('tr[pid=' + val + ']').show();
        } else {
            loc.removeClass('glyphicon-minus');
            loc.addClass('glyphicon-plus');
            $('tr[pid=' + val + ']').hide();
        }
    });

    $('#btnSave').click(function () {
        var data = {};
        var sel = $('input,select');
        sel.each(function (index, el) {
            var loc = $(el);
            data[loc.attr('id')] = loc.val();
        });
        if (data.name == '') {
            $.showToast('请输入菜单名')
            return
        }
        $.ajax({
            type: "POST",
            url: '/sys/addMenu',
            data: data,
            dataType: 'json',
            success: function (resp) {
                if (resp.success) {
                    $.hideModel();
                    window.location.reload()
                    return
                }
                $.showToast(resp.info)
            },
            error: function (resp) {
                $.showToast('请求错误，请稍后再试', null);
            }
        });
    });
});

function addMenu(pid) {
    var sel = $('input');
    sel.each(function (index, el) {
        $(el).val(null);
    });
    $('#pid').val(pid);
    $('#id').val(0)
    $.showModel('#popModel');
}

function editMenu(id) {
    var data = {
        id: id
    };
    $.ajax({
        type: "POST",
        url: '/sys/getMenuById',
        data: data,
        dataType: 'json',
        //contentType: 'application/json',
        success: function (resp) {
            var sel = $('input,select');
            sel.each(function (index, el) {
                var loc = $(el);
                //console.log(loc.attr('id')+":"+resp[loc.attr('id')])
                loc.val(resp[loc.attr('id')]);
            });
        },
        error: function (resp) {
            $.showToast('请求错误，请稍后再试', null);
        }
    });
    $.showModel('#popModel');
}

function deleteMenu(id) {
    var data = {
        id: id
    };
    $.showConfirm('popConfirm','btnYes',function(){
        $.ajax({
            type: "POST",
            url: '/sys/deleteMenu',
            data: data,
            dataType: 'json',
            success: function (resp) {
                if (resp.success) {
                    window.location.reload()
                    return
                }
                $.showToast(resp.info)
            },
            error: function (resp) {
                $.showToast('请求错误，请稍后再试', null);
            }
        });
    });

}