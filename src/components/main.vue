<template>
    <div class="container">
        <aside>
            <div class="asHead">Admin</div>
            <div class="menu">123</div>
        </aside>
        <article>
            <div class="mainHead">
                <div class="mainHead-right"></div>
            </div>
            <main>
                <router-view></router-view>
                main界面
            </main>
        </article>
    </div>
</template>

<script>

export default {
    data() {
        return {

        }
    },
}
</script>

<style src="@/assets/main.css" scoped>
</style>