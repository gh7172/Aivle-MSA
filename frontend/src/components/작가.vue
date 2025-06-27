<template>
    <div>
        <Number
            label="UserId"
            v-model="value.userId"
            :editMode="editMode"
        />
        <String
            label="PenName"
            v-model="value.penName"
            :editMode="editMode"
        />
        <String
            label="Portfolio"
            v-model="value.portfolio"
            :editMode="editMode"
        />
        <String
            label="SelfIntroduction"
            v-model="value.selfIntroduction"
            :editMode="editMode"
        />
        <v-row class="ma-0 pa-0">
            <v-spacer></v-spacer>
            <v-btn width="64px" color="primary" @click="save">
                저장
            </v-btn>
        </v-row>
    </div>
</template>


<script>
import BaseEntity from './base-ui/BaseEntity.vue'

export default {
    name: '작가',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "작가",
        value: {
        }
    }),
    created(){
    },
    computed:{
    },
    methods: {
    },
}
</script>
