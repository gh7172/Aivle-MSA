<script setup>
import {
  VerticalNavLink,
  VerticalNavSectionTitle,
} from '@layouts'
</script>

<template>
    <ul>
        <VerticalNavSectionTitle :item="{ heading: 'Author' }" />
        <VerticalNavLink
            :item="{
                title: '작가',
                to: '/작가',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '집필' }" />
        <VerticalNavLink
            :item="{
                title: '집필',
                to: '/집필',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '도서' }" />
        <VerticalNavLink
            :item="{
                title: '도서',
                to: '/도서',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '구독' }" />
        <VerticalNavLink
            :item="{
                title: '구독',
                to: '/subscribes',
            }"
        />
        <VerticalNavLink
            :item="{
                title: 'GetSubscribe',
                to: '/getSubscribes',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '유저' }" />
        <VerticalNavLink
            :item="{
                title: '유저',
                to: '/유저',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: 'Point' }" />
        <VerticalNavLink
            :item="{
                title: '포인트',
                to: '/points',
            }"
        />
        <VerticalNavLink
            :item="{
                title: 'GetPoint',
                to: '/getPoints',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '생성' }" />
        <VerticalNavLink
            :item="{
                title: '집필요청',
                to: '/집필요청',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: 'Purchase' }" />
        <VerticalNavLink
            :item="{
                title: '구매',
                to: '/pays',
            }"
        />
        <VerticalNavLink
            :item="{
                title: 'GetPay',
                to: '/getPays',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '유저센터' }" />
        <VerticalNavLink
            :item="{
                title: '유저정보',
                to: '/userInfos',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: 'Read' }" />
        <VerticalNavLink
            :item="{
                title: '',
                to: '/',
            }"
        />
    </ul>
</template>
