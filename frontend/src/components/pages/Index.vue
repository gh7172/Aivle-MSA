<template>
  <v-container fluid>
    <v-row>
      <v-card
          v-for="(card, index) in cards"
          :key="index"
          class="mx-auto"
          style="height:300px; width:300px; margin-bottom:20px;"
          outlined
      >
        <v-list-item>
          <v-icon style="margin-top: 30%; margin-left: 37.5%;" color="primary" size="64">mdi-apps</v-icon>
        </v-list-item>
        <v-card-actions>
          <v-btn
              class="mx-auto"
              outlined
              rounded
              :to="card.link"
              style="font-weight:500; font-size:20px; border:solid 2px; max-width:250px; overflow:hidden; margin-top: 10%;"
          >
              {{ card.text }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: 'Index',
  data: () => ({
    cards: [
      {
          text: "작가",
          link: "/작가",
      },
      {
          text: "집필",
          link: "/집필",
      },
      {
          text: "도서",
          link: "/도서",
      },
      {
          text: "구독",
          link: "/subscribes",
      },
      {
          text: "GetSubscribe",
          link: "/getSubscribes",
      },
      {
          text: "유저",
          link: "/유저",
      },
      {
          text: "포인트",
          link: "/points",
      },
      {
          text: "GetPoint",
          link: "/getPoints",
      },
      {
          text: "집필요청",
          link: "/집필요청",
      },
      {
          text: "구매",
          link: "/pays",
      },
      {
          text: "GetPay",
          link: "/getPays",
      },
      {
          text: "유저정보",
          link: "/userInfos",
      },
      {
          text: "",
          link: "/",
      },
    ],
  }),
};
</script>
