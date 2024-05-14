<template>
    <div v-if="showForm" class="form-container">
        <form @submit.prevent="handleSubmit">
        <input type="text" v-model="formData.amount" placeholder="输入金额" required>
            <input type="text" v-model="formData.note" placeholder="输入备注" required>
                <button type="submit">提交</button>
            </form>
    </div>
</template>

<script>
    export default {
    props: {
    showForm: Boolean,
    formData: Object
},
    methods: {
    handleSubmit() {
    // 在这里处理表单提交逻辑，比如发送到后台API
    console.log('Form submitted:', this.formData);
    // 假设你有一个方法用来关闭表单
    this.$emit('closeForm');
}
}
};
</script>