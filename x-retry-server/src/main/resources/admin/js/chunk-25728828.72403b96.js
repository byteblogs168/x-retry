(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-25728828"],{"0fea":function(e,t,n){"use strict";n.d(t,"d",(function(){return o})),n.d(t,"b",(function(){return i})),n.d(t,"k",(function(){return s})),n.d(t,"h",(function(){return u})),n.d(t,"n",(function(){return c})),n.d(t,"m",(function(){return l})),n.d(t,"j",(function(){return d})),n.d(t,"i",(function(){return f})),n.d(t,"g",(function(){return p})),n.d(t,"f",(function(){return m})),n.d(t,"r",(function(){return y})),n.d(t,"a",(function(){return h})),n.d(t,"q",(function(){return g})),n.d(t,"p",(function(){return b})),n.d(t,"e",(function(){return v})),n.d(t,"c",(function(){return k})),n.d(t,"s",(function(){return S})),n.d(t,"t",(function(){return w})),n.d(t,"l",(function(){return _})),n.d(t,"o",(function(){return O}));var r=n("b775"),a={user:"/user",role:"/role",service:"/service",permission:"/permission",permissionNoPager:"/permission/no-pager",orgTree:"/org/tree",groupConfigForPage:"/group/list",saveGroup:"/group",groupConfigByGroupName:"/group",allGroupNameList:"/group/all/group-name/list",retryTaskPage:"/retry-task/list",retryTaskById:"/retry-task/",retryTaskLogPage:"/retry-task-log/list",retryTaskLogById:"/retry-task-log/",retryDeadLetterPage:"/retry-dead-letter/list",retryDeadLetterById:"/retry-dead-letter/",retryDeadLetterRollback:"/retry-dead-letter/rollback/",deleteRetryDeadLetter:"/retry-dead-letter/",scenePageList:"/scene-config/page/list",sceneList:"/scene-config/list",notifyConfigList:"/notify-config/list",userPageList:"/user/page/list",saveUser:"/user",systemUserByUserName:"/user/username/user-info"};function o(e){return Object(r["b"])({url:a.groupConfigForPage,method:"get",params:e})}function i(e){return Object(r["b"])({url:a.allGroupNameList,method:"get",params:e})}function s(e){return Object(r["b"])({url:a.retryTaskPage,method:"get",params:e})}function u(e,t){return Object(r["b"])({url:a.retryTaskById+e,method:"get",params:t})}function c(e){return Object(r["b"])({url:a.scenePageList,method:"get",params:e})}function l(e){return Object(r["b"])({url:a.sceneList,method:"get",params:e})}function d(e){return Object(r["b"])({url:a.retryTaskLogPage,method:"get",params:e})}function f(e){return Object(r["b"])({url:a.retryTaskLogById+e,method:"get"})}function p(e){return Object(r["b"])({url:a.retryDeadLetterPage,method:"get",params:e})}function m(e,t){return Object(r["b"])({url:a.retryDeadLetterById+e,method:"get",params:t})}function y(e,t){return Object(r["b"])({url:a.retryDeadLetterRollback+e,method:"get",params:t})}function h(e,t){return Object(r["b"])({url:a.deleteRetryDeadLetter+e,method:"delete",params:t})}function g(e){return Object(r["b"])({url:a.userPageList,method:"get",params:e})}function b(e){return Object(r["b"])({url:a.systemUserByUserName,method:"get",params:e})}function v(e){return Object(r["b"])({url:a.notifyConfigList,method:"get",params:e})}function k(e){return Object(r["b"])({url:a.groupConfigByGroupName+"/".concat(e),method:"get"})}function S(e){return Object(r["b"])({url:a.saveGroup,method:0===e.id?"post":"put",data:e})}function w(e){return Object(r["b"])({url:a.saveUser,method:void 0===e.id?"post":"put",data:e})}function _(e){return Object(r["b"])({url:a.role,method:"get",params:e})}function O(e){return Object(r["b"])({url:a.service,method:"get",params:e})}},"32f0":function(e,t,n){},"432b":function(e,t,n){"use strict";n.d(t,"a",(function(){return o}));var r=n("5530"),a=n("5880"),o={computed:Object(r["a"])(Object(r["a"])({},Object(a["mapState"])({layout:function(e){return e.app.layout},navTheme:function(e){return e.app.theme},primaryColor:function(e){return e.app.color},colorWeak:function(e){return e.app.weak},fixedHeader:function(e){return e.app.fixedHeader},fixedSidebar:function(e){return e.app.fixedSidebar},contentWidth:function(e){return e.app.contentWidth},autoHideHeader:function(e){return e.app.autoHideHeader},isMobile:function(e){return e.app.isMobile},sideCollapsed:function(e){return e.app.sideCollapsed},multiTab:function(e){return e.app.multiTab}})),{},{isTopMenu:function(){return"topmenu"===this.layout}}),methods:{isSideMenu:function(){return!this.isTopMenu}}}},"46bd":function(e,t,n){"use strict";n("32f0")},"88bc":function(e,t,n){(function(t){var n=1/0,r=9007199254740991,a="[object Arguments]",o="[object Function]",i="[object GeneratorFunction]",s="[object Symbol]",u="object"==typeof t&&t&&t.Object===Object&&t,c="object"==typeof self&&self&&self.Object===Object&&self,l=u||c||Function("return this")();function d(e,t,n){switch(n.length){case 0:return e.call(t);case 1:return e.call(t,n[0]);case 2:return e.call(t,n[0],n[1]);case 3:return e.call(t,n[0],n[1],n[2])}return e.apply(t,n)}function f(e,t){var n=-1,r=e?e.length:0,a=Array(r);while(++n<r)a[n]=t(e[n],n,e);return a}function p(e,t){var n=-1,r=t.length,a=e.length;while(++n<r)e[a+n]=t[n];return e}var m=Object.prototype,y=m.hasOwnProperty,h=m.toString,g=l.Symbol,b=m.propertyIsEnumerable,v=g?g.isConcatSpreadable:void 0,k=Math.max;function S(e,t,n,r,a){var o=-1,i=e.length;n||(n=C),a||(a=[]);while(++o<i){var s=e[o];t>0&&n(s)?t>1?S(s,t-1,n,r,a):p(a,s):r||(a[a.length]=s)}return a}function w(e,t){return e=Object(e),_(e,t,(function(t,n){return n in e}))}function _(e,t,n){var r=-1,a=t.length,o={};while(++r<a){var i=t[r],s=e[i];n(s,i)&&(o[i]=s)}return o}function O(e,t){return t=k(void 0===t?e.length-1:t,0),function(){var n=arguments,r=-1,a=k(n.length-t,0),o=Array(a);while(++r<a)o[r]=n[t+r];r=-1;var i=Array(t+1);while(++r<t)i[r]=n[r];return i[t]=o,d(e,this,i)}}function C(e){return N(e)||x(e)||!!(v&&e&&e[v])}function j(e){if("string"==typeof e||q(e))return e;var t=e+"";return"0"==t&&1/e==-n?"-0":t}function x(e){return L(e)&&y.call(e,"callee")&&(!b.call(e,"callee")||h.call(e)==a)}var N=Array.isArray;function T(e){return null!=e&&R(e.length)&&!I(e)}function L(e){return P(e)&&T(e)}function I(e){var t=D(e)?h.call(e):"";return t==o||t==i}function R(e){return"number"==typeof e&&e>-1&&e%1==0&&e<=r}function D(e){var t=typeof e;return!!e&&("object"==t||"function"==t)}function P(e){return!!e&&"object"==typeof e}function q(e){return"symbol"==typeof e||P(e)&&h.call(e)==s}var A=O((function(e,t){return null==e?{}:w(e,f(S(t,1),j))}));e.exports=A}).call(this,n("c8ba"))},e941:function(e,t,n){"use strict";n.r(t);var r=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("page-header-wrapper",{attrs:{content:"配置组、场景、通知配置"}},[n("a-card",{staticClass:"card",attrs:{title:"组配置",bordered:!1}},[n("group-form",{ref:"groupConfig",attrs:{showSubmit:!1}})],1),n("a-card",{staticClass:"card",attrs:{title:"通知配置",bordered:!1}},[n("notify-list",{ref:"notify"})],1),n("a-card",{staticClass:"card",attrs:{title:"场景配置",bordered:!1}},[n("scene-list",{ref:"scene"})],1),n("footer-tool-bar",{attrs:{"is-mobile":e.isMobile,collapsed:e.sideCollapsed}},[n("span",{staticClass:"popover-wrapper"},[n("a-popover",{attrs:{title:"表单校验信息",overlayClassName:"antd-pro-pages-forms-style-errorPopover",trigger:"click",getPopupContainer:function(e){return e.parentNode}}},[n("template",{slot:"content"},e._l(e.errors,(function(t){return n("li",{key:t.key,staticClass:"antd-pro-pages-forms-style-errorListItem",on:{click:function(n){return e.scrollToField(t.key)}}},[n("a-icon",{staticClass:"antd-pro-pages-forms-style-errorIcon",attrs:{type:"cross-circle-o"}}),n("div",{},[e._v(e._s(t.message))]),n("div",{staticClass:"antd-pro-pages-forms-style-errorField"},[e._v(e._s(t.fieldLabel))])],1)})),0),e.errors.length>0?n("span",{staticClass:"antd-pro-pages-forms-style-errorIcon"},[n("a-icon",{attrs:{type:"exclamation-circle"}}),e._v(e._s(e.errors.length)+" ")],1):e._e()],2)],1),n("a-button",{attrs:{type:"primary",loading:e.loading},on:{click:e.validate}},[e._v("提交")])],1)],1)},a=[],o=n("5530"),i=(n("d3b7"),n("d81d"),n("4de4"),n("b64b"),function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("a-form",{staticClass:"form",attrs:{form:e.form},on:{submit:e.handleSubmit}},[n("a-row",{staticClass:"form-row",attrs:{gutter:16}},[n("a-col",{attrs:{lg:6,md:12,sm:24}},[n("a-form-item",{attrs:{label:"组名称"}},[n("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["id"],expression:"[\n            'id',\n          ]"}],attrs:{placeholder:"请输入组名称",hidden:""}}),n("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["groupName",{rules:[{required:!0,message:"请输入组名称",whitespace:!0}]}],expression:"[\n            'groupName',\n            {rules: [{ required: true, message: '请输入组名称', whitespace: true}]}\n          ]"}],attrs:{placeholder:"请输入组名称"}})],1)],1),n("a-col",{attrs:{lg:6,md:12,sm:24}},[n("a-form-item",{attrs:{label:"状态"}},[n("a-select",{directives:[{name:"decorator",rawName:"v-decorator",value:["groupStatus",{rules:[{required:!0,message:"请选择状态类型"}]}],expression:"[\n            'groupStatus',\n            {rules: [{ required: true, message: '请选择状态类型'}]}\n          ]"}],attrs:{placeholder:"请选择状态"}},[n("a-select-option",{attrs:{value:"0"}},[e._v("停用")]),n("a-select-option",{attrs:{value:"1"}},[e._v("启动")])],1)],1)],1),n("a-col",{attrs:{lg:6,md:12,sm:24}},[n("a-form-item",{attrs:{label:"路由策略"}},[n("a-select",{directives:[{name:"decorator",rawName:"v-decorator",value:["routeKey",{rules:[{required:!0,message:"请选择路由策略"}]}],expression:"[\n            'routeKey',\n            {rules: [{ required: true, message: '请选择路由策略'}]}\n          ]"}],attrs:{placeholder:"请选择路由策略"}},e._l(e.routeKey,(function(t,r){return n("a-select-option",{key:r,attrs:{value:r}},[e._v(e._s(t))])})),1)],1)],1),n("a-col",{attrs:{lg:6,md:12,sm:24}},[n("a-form-item",{attrs:{label:"描述"}},[n("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["description",{rules:[{required:!0,message:"请输入描述",whitespace:!0}]}],expression:"[\n            'description',\n            {rules: [{ required: true, message: '请输入描述', whitespace: true}]}\n          ]"}],attrs:{placeholder:"请输入描述"}})],1)],1),n("a-col",{attrs:{lg:3,md:6,sm:12}},[n("a-form-item",{attrs:{label:"指定分区"}},[n("a-input-number",{directives:[{name:"decorator",rawName:"v-decorator",value:["groupPartition"],expression:"[\n            'groupPartition'\n          ]"}],attrs:{id:"inputNumber",placeholder:"分区",min:1,max:10}})],1)],1)],1),e.showSubmit?n("a-form-item",[n("a-button",{attrs:{htmlType:"submit"}},[e._v("Submit")])],1):e._e()],1)}),s=[],u=(n("ac1f"),n("25f0"),n("0fea")),c=n("88bc"),l=n.n(c),d={name:"GroupForm",props:{showSubmit:{type:Boolean,default:!1}},data:function(){return{form:this.$form.createForm(this),routeKey:{1:"一致性hash算法",2:"随机算法",3:"最近最久未使用算法"}}},mounted:function(){var e=this;this.$nextTick((function(){var t=e.$route.query.groupName;t&&Object(u["c"])(t).then((function(t){e.loadEditInfo(t.data)}))}))},methods:{handleSubmit:function(e){var t=this;e.preventDefault(),this.form.validateFields((function(e,n){e||t.$notification["error"]({message:"Received values of form:",description:n})}))},validate:function(e,t,n){var r=/^user-(.*)$/;r.test(t)||n(new Error("需要以 user- 开头")),n()},loadEditInfo:function(e){var t=this.form;new Promise((function(e){setTimeout(e,1500)})).then((function(){var n=l()(e,["id","groupName","routeKey","groupStatus","description","groupPartition"]);n.groupStatus=n.groupStatus.toString(),n.routeKey=n.routeKey.toString(),t.setFieldsValue(n)}))}}},f=d,p=n("2877"),m=Object(p["a"])(f,i,s,!1,null,"296629aa",null),y=m.exports,h=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",[n("div",{staticClass:"table-page-search-wrapper"},[n("a-form",{attrs:{layout:"inline"}},[n("a-row",{attrs:{gutter:48}},[[n("a-col",{attrs:{md:8,sm:24}},[n("a-form-item",{attrs:{label:"场景名称"}},[n("a-input",{attrs:{placeholder:"请输入场景名称"},model:{value:e.queryParam.sceneName,callback:function(t){e.$set(e.queryParam,"sceneName",t)},expression:"queryParam.sceneName"}})],1)],1)],n("a-col",{attrs:{md:e.advanced?24:8,sm:24}},[n("span",{staticClass:"table-page-search-submitButtons",style:e.advanced&&{float:"right",overflow:"hidden"}||{}},[n("a-button",{attrs:{type:"primary"},on:{click:function(t){return e.queryChange()}}},[e._v("查询")]),n("a-button",{staticStyle:{"margin-left":"8px"},on:{click:function(){return e.queryParam={}}}},[e._v("重置")])],1)])],2)],1)],1),n("a-table",{attrs:{columns:e.sceneColumns,"row-key":function(e){return e.key},dataSource:e.data,pagination:e.pagination,loading:e.memberLoading},on:{change:e.handleTableChange},scopedSlots:e._u([e._l(["sceneName","description"],(function(t,r){return{key:t,fn:function(r,a){return[a.editable?n("a-input",{key:t,staticStyle:{margin:"-5px 0"},attrs:{value:r,placeholder:e.sceneColumns.find((function(e){return e.key===t})).title},on:{change:function(n){return e.handleChange(n.target.value,a.key,t)}}}):[e._v(e._s(r))]]}}})),{key:"sceneStatus",fn:function(t,r){return[r.editable?n("a-select",{staticStyle:{width:"100%"},attrs:{placeholder:"场景状态",value:0===t?"1":t},on:{change:function(t){return e.handleChange(t,r.key,"sceneStatus")}}},[n("a-select-option",{attrs:{value:"0"}},[e._v("停用")]),n("a-select-option",{attrs:{value:"1"}},[e._v("启用")])],1):[e._v(e._s(e.sceneStatus[t]))]]}},{key:"backOff",fn:function(t,r){return[r.editable?n("a-select",{staticStyle:{width:"100%"},attrs:{placeholder:"退避策略",value:0===t?null:t},on:{change:function(t){return e.handleChange(t,r.key,"backOff")}}},[n("a-select-option",{attrs:{value:"1"}},[e._v("延迟等级")]),n("a-select-option",{attrs:{value:"2"}},[e._v("固定定时间")]),n("a-select-option",{attrs:{value:"3"}},[e._v("CRON表达式")]),n("a-select-option",{attrs:{value:"4"}},[e._v("随机等待")])],1):[e._v(e._s(e.backOffLabels[t]))]]}},{key:"maxRetryCount",fn:function(t,r){return[r.editable?n("a-input-number",{staticStyle:{width:"100%"},attrs:{min:1,max:99999,value:t,placeholder:"最大重试次数"},on:{change:function(t){return e.handleChange(t,r.key,"maxRetryCount")}}}):[e._v(e._s(t))]]}},{key:"triggerInterval",fn:function(t,r){return[r.editable?n("a-input",{staticStyle:{margin:"-5px 0"},attrs:{placeholder:"间隔时间",value:t,disabled:"1"===e.data.find((function(e){return e.key===r.key})).backOff},on:{change:function(t){return e.handleChange(t.target.value,r.key,"triggerInterval")}}}):[e._v(e._s(t))]]}},{key:"operation",fn:function(t,r){return[r.editable?[r.isNew?n("span",[n("a",{on:{click:function(t){return e.saveRow(r)}}},[e._v("添加")]),n("a-divider",{attrs:{type:"vertical"}}),n("a-popconfirm",{attrs:{title:"是否要删除此行？"},on:{confirm:function(t){return e.remove(r.key)}}},[n("a",[e._v("删除")])])],1):n("span",[n("a",{on:{click:function(t){return e.saveRow(r)}}},[e._v("保存")]),n("a-divider",{attrs:{type:"vertical"}}),n("a",{on:{click:function(t){return e.cancel(r.key)}}},[e._v("取消")])],1)]:n("span",[n("a",{on:{click:function(t){return e.toggle(r.key)}}},[e._v("编辑")]),n("a-divider",{attrs:{type:"vertical"}}),n("a-popconfirm",{attrs:{title:"是否要删除此行？"},on:{confirm:function(t){return e.remove(r.key)}}},[n("a",[e._v("删除")])])],1)]}}],null,!0)}),n("a-button",{staticStyle:{width:"100%","margin-top":"16px","margin-bottom":"8px"},attrs:{type:"dashed",icon:"plus"},on:{click:e.newMember}},[e._v("新增成员")])],1)},g=[],b=n("6b75");function v(e){if(Array.isArray(e))return Object(b["a"])(e)}n("a4d3"),n("e01a"),n("d28b"),n("3ca3"),n("ddb0"),n("a630");function k(e){if("undefined"!==typeof Symbol&&null!=e[Symbol.iterator]||null!=e["@@iterator"])return Array.from(e)}var S=n("06c5");function w(){throw new TypeError("Invalid attempt to spread non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}function _(e){return v(e)||k(e)||Object(S["a"])(e)||w()}n("7db0"),n("159b");var O=n("2af9"),C={name:"SceneList",components:{STable:O["j"]},data:function(){return{sceneColumns:[{title:"场景名称",dataIndex:"sceneName",key:"sceneName",width:"15%",scopedSlots:{customRender:"sceneName"}},{title:"场景状态",dataIndex:"sceneStatus",key:"sceneStatus",width:"12%",scopedSlots:{customRender:"sceneStatus"}},{title:"退避策略",dataIndex:"backOff",key:"backOff",width:"12%",scopedSlots:{customRender:"backOff"}},{title:"最大重试次数",dataIndex:"maxRetryCount",key:"maxRetryCount",width:"12%",scopedSlots:{customRender:"maxRetryCount"}},{title:"间隔时间",dataIndex:"triggerInterval",key:"triggerInterval",width:"12%",scopedSlots:{customRender:"triggerInterval"}},{title:"描述",dataIndex:"description",key:"description",width:"25%",scopedSlots:{customRender:"description"}},{title:"操作",key:"action",scopedSlots:{customRender:"operation"}}],data:[],formData:[],loading:!1,advanced:!1,memberLoading:!1,triggerIntervalDisabled:!1,max:21,pagination:{},backOffLabels:{1:"延迟等级",2:"固定定时间",3:"CRON表达式",4:"随机等待"},sceneStatus:{0:"停用",1:"启用"},queryParam:{}}},created:function(){var e=this.$route.query.groupName;e&&this.fetch({groupName:e,size:6,page:1})},methods:{handleTableChange:function(e,t,n){var r=Object(o["a"])({},this.pagination);r.current=e.current,this.pagination=r,this.fetch(Object(o["a"])({groupName:this.$route.query.groupName,size:e.pageSize,page:e.current,sortField:n.field,sortOrder:n.order},t))},queryChange:function(){this.fetch({groupName:this.$route.query.groupName,size:6,page:1,sceneName:this.queryParam.sceneName})},fetch:function(){var e=this,t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};this.loading=!0,Object(u["n"])(t).then((function(t){e.data=[],t.data.map((function(t){e.loading=!1;var n=t.id,r=t.sceneName,a=t.sceneStatus,o=t.maxRetryCount,i=t.backOff,s=t.triggerInterval,u=t.description;e.data.push({key:n,sceneName:r,sceneStatus:a.toString(),maxRetryCount:o,backOff:i.toString(),triggerInterval:s,description:u,editable:!1,isNew:!1})}));var n=Object(o["a"])({},e.pagination);n.pageSize=t.size,n.current=t.page,n.total=t.total,e.pagination=n}))},remove:function(e){var t=this.data.find((function(t){return t.key===e})),n=t.key,r=t.sceneName,a=t.sceneStatus,o=t.maxRetryCount,i=t.backOff,s=t.triggerInterval,u=t.description;this.formData.push({key:n,sceneName:r,sceneStatus:a,maxRetryCount:o,backOff:i,triggerInterval:s,description:u,isDeleted:1});var c=this.data.filter((function(t){return t.key!==e}));this.data=c},saveRow:function(e){var t=this;this.memberLoading=!0;var n=e.key,r=e.sceneName,a=e.sceneStatus,o=e.maxRetryCount,i=e.backOff,s=e.triggerInterval,u=e.description;if(!r||!a||!o||!i||"1"!==i&&!s)return this.memberLoading=!1,void this.$message.error("请填写完整成员信息。");var c=this.formData.find((function(e){return n===e.key}));c||this.formData.push({key:n,sceneName:r,sceneStatus:a,maxRetryCount:o,backOff:i,triggerInterval:s,description:u,isDeleted:0}),new Promise((function(e){setTimeout((function(){e({loop:!1})}),200)})).then((function(){var e=t.data.find((function(e){return e.key===n}));e.editable=!1,e.isNew=!1,t.memberLoading=!1}))},toggle:function(e){var t=this.data.find((function(t){return t.key===e}));t._originalData=Object(o["a"])({},t),t.editable=!t.editable},getRowByKey:function(e,t){var n=this.data;return(t||n).find((function(t){return t.key===e}))},cancel:function(e){var t=this.data.find((function(t){return t.key===e}));Object.keys(t).forEach((function(e){t[e]=t._originalData[e]})),t._originalData=void 0},handleChange:function(e,t,n){if("backOff"===n)switch(e){case"1":this.triggerIntervalDisabled=!0,this.max=21;break;default:this.triggerIntervalDisabled=!1,this.max=99999}var r=_(this.data),a=r.find((function(e){return t===e.key}));a&&(a[n]=e,this.data=r)},newMember:function(){var e=this.data.length;this.data.unshift({key:0===e?"1":(parseInt(this.data[e-1].key)+1).toString(),sceneName:"",sceneStatus:"1",maxRetryCount:null,backOff:"1",triggerInterval:"",description:"",editable:!0,isNew:!0})}}},j=C,x=Object(p["a"])(j,h,g,!1,null,"46a2227e",null),N=x.exports,T=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",[n("a-table",{attrs:{columns:e.notifyColumns,dataSource:e.data,pagination:!1,loading:e.memberLoading},scopedSlots:e._u([e._l(["notifyAddress","description"],(function(t,r){return{key:t,fn:function(r,a){return[a.editable?n("a-input",{key:t,staticStyle:{margin:"-5px 0"},attrs:{value:r,placeholder:e.notifyColumns.find((function(e){return e.key===t})).title},on:{change:function(n){return e.handleChange(n.target.value,a.key,t)}}}):[e._v(e._s(r))]]}}})),{key:"notifyScene",fn:function(t,r){return[r.editable?n("a-select",{staticStyle:{width:"100%"},attrs:{placeholder:"通知场景",value:t},on:{change:function(t){return e.handleChange(t,r.key,"notifyScene")}}},e._l(e.notifyScene,(function(t,r){return n("a-select-option",{key:r,attrs:{value:r}},[e._v(e._s(t))])})),1):[e._v(e._s(e.notifyScene[t]))]]}},{key:"notifyType",fn:function(t,r){return[r.editable?n("a-select",{staticStyle:{width:"100%"},attrs:{placeholder:"通知类型",value:t},on:{change:function(t){return e.handleChange(t,r.key,"notifyType")}}},e._l(e.notifyType,(function(t,r){return n("a-select-option",{key:r,attrs:{value:r}},[e._v(e._s(t))])})),1):[e._v(e._s(e.notifyType[t]))]]}},{key:"notifyThreshold",fn:function(t,r){return[r.editable?n("a-input-number",{staticStyle:{width:"100%"},attrs:{min:1,max:999999,value:t,disabled:e.notifyThresholdDisabled.includes(e.data.find((function(e){return e.key===r.key})).notifyScene),placeholder:"通知阈值"},on:{change:function(t){return e.handleChange(t,r.key,"notifyThreshold")}}}):[e._v(e._s(t))]]}},{key:"operation",fn:function(t,r){return[r.editable?[r.isNew?n("span",[n("a",{on:{click:function(t){return e.saveRow(r)}}},[e._v("添加")]),n("a-divider",{attrs:{type:"vertical"}}),n("a-popconfirm",{attrs:{title:"是否要删除此行？"},on:{confirm:function(t){return e.remove(r.key)}}},[n("a",[e._v("删除")])])],1):n("span",[n("a",{on:{click:function(t){return e.saveRow(r)}}},[e._v("保存")]),n("a-divider",{attrs:{type:"vertical"}}),n("a",{on:{click:function(t){return e.cancel(r.key)}}},[e._v("取消")])],1)]:n("span",[n("a",{on:{click:function(t){return e.toggle(r.key)}}},[e._v("编辑")]),n("a-divider",{attrs:{type:"vertical"}}),n("a-popconfirm",{attrs:{title:"是否要删除此行？"},on:{confirm:function(t){return e.remove(r.key)}}},[n("a",[e._v("删除")])])],1)]}}],null,!0)}),n("a-button",{staticStyle:{width:"100%","margin-top":"16px","margin-bottom":"8px"},attrs:{type:"dashed",icon:"plus"},on:{click:e.newMember}},[e._v("新增成员")])],1)},L=[],I=(n("caad"),n("2532"),{name:"NotifyList",data:function(){return{notifyColumns:[{title:"通知类型",dataIndex:"notifyType",key:"notifyType",width:"12%",scopedSlots:{customRender:"notifyType"}},{title:"通知场景",dataIndex:"notifyScene",key:"notifyScene",width:"15%",scopedSlots:{customRender:"notifyScene"}},{title:"通知阈值",dataIndex:"notifyThreshold",key:"notifyThreshold",width:"12%",scopedSlots:{customRender:"notifyThreshold"}},{title:"通知地址",dataIndex:"notifyAddress",key:"notifyAddress",width:"25%",scopedSlots:{customRender:"notifyAddress"}},{title:"描述",dataIndex:"description",key:"description",width:"25%",scopedSlots:{customRender:"description"}},{title:"操作",key:"action",scopedSlots:{customRender:"operation"}}],data:[],formData:[],loading:!1,memberLoading:!1,notifyScene:{1:"重试数量超过阈值",2:"重试失败数量超过阈值",3:"客户端上报失败",4:"客户端组件异常"},notifyType:{1:"钉钉通知",2:"邮箱通知",3:"企业微信"},notifyThresholdDisabled:["3","4"]}},created:function(){var e=this,t=this.$route.query.groupName;t&&Object(u["e"])({groupName:t}).then((function(t){t.data.map((function(t){var n=t.id,r=t.notifyType,a=t.notifyThreshold,o=t.notifyScene,i=t.description,s=t.notifyAddress;e.data.push({key:n,notifyType:r.toString(),notifyThreshold:a,notifyScene:o.toString(),description:i,notifyAddress:s,editable:!1,isNew:!1})}))}))},methods:{remove:function(e){var t=this.data.find((function(t){return e===t.key})),n=t.key,r=t.notifyType,a=t.notifyThreshold,o=t.notifyAddress,i=t.notifyScene,s=t.description;this.formData.push({key:n,notifyType:r,notifyThreshold:a,notifyScene:i,notifyAddress:o,description:s,isDeleted:1});var u=this.data.filter((function(e){return e.key!==n}));this.data=u},saveRow:function(e){var t=this;this.memberLoading=!0;var n=e.key,r=e.notifyType,a=e.notifyThreshold,o=e.notifyAddress,i=e.notifyScene,s=e.description;if(!r||!i||!o||!s||!this.notifyThresholdDisabled.includes(i)&&!a)return this.memberLoading=!1,void this.$message.error("请填写完整成员信息。");var u=this.formData.find((function(e){return n===e.key}));u||this.formData.push({key:n,notifyType:r,notifyThreshold:a,notifyScene:i,notifyAddress:o,description:s,isDeleted:0}),new Promise((function(e){setTimeout((function(){e({loop:!1})}),100)})).then((function(){var e=t.data.find((function(e){return e.key===n}));e.editable=!1,e.isNew=!1,t.memberLoading=!1}))},toggle:function(e){var t=this.data.find((function(t){return t.key===e}));t._originalData=Object(o["a"])({},t),t.editable=!t.editable},getRowByKey:function(e,t){var n=this.data;return(t||n).find((function(t){return t.key===e}))},cancel:function(e){var t=this.data.find((function(t){return t.key===e}));Object.keys(t).forEach((function(e){t[e]=t._originalData[e]})),t._originalData=void 0},handleChange:function(e,t,n){var r=_(this.data),a=r.find((function(e){return t===e.key}));a&&(a[n]=e,this.data=r)},newMember:function(){var e=this.data.length;this.data.push({key:0===e?"1":(parseInt(this.data[e-1].key)+1).toString(),notifyType:"1",notifyScene:"1",notifyThreshold:null,notifyAddress:"",description:"",editable:!0,isNew:!0})}}}),R=I,D=Object(p["a"])(R,T,L,!1,null,"311caa31",null),P=D.exports,q=n("5a70"),A=n("432b"),F={groupName:"组名称",groupStatus:"组状态",description:"描述"},$={name:"AdvancedForm",mixins:[A["a"]],components:{FooterToolBar:q["a"],GroupForm:y,SceneList:N,NotifyList:P},data:function(){return{loading:!1,memberLoading:!1,errors:[]}},methods:{handleSubmit:function(e){e.preventDefault()},validate:function(){var e=this,t=this.$refs,n=t.groupConfig,r=t.scene,a=t.notify,i=this.$notification,s=new Promise((function(e,t){n.form.validateFields((function(n,r){n?t(n):e(r)}))}));this.errors=[],s.then((function(e){e["id"]||(e["id"]=0),e["sceneList"]=r.formData,e["notifyList"]=a.formData,Object(u["s"])(e).then((function(e){0===e.status?i["error"]({message:e.message}):i["success"]({message:e.message})}))})).catch((function(){var t=Object.assign({},n.form.getFieldsError()),r=Object(o["a"])({},t);e.errorList(r)}))},errorList:function(e){e&&0!==e.length&&(this.errors=Object.keys(e).filter((function(t){return e[t]})).map((function(t){return{key:t,message:e[t][0],fieldLabel:F[t]}})))},scrollToField:function(e){var t=document.querySelector('label[for="'.concat(e,'"]'));t&&t.scrollIntoView(!0)}}},B=$,E=(n("46bd"),Object(p["a"])(B,r,a,!1,null,"2f27469c",null));t["default"]=E.exports}}]);