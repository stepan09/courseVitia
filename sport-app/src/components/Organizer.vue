<template>
    <div class="container">
      <div class="row">
        <div class="col-md-8">
          <input type="text" v-model="search" class="form-control" placeholder="Пошук">
        </div>
        <div class="col-md-1">
          <button type="button" @click="showModal = true" class="btn btn-success" data-toggle="modal" data-target="#exampleModalCenter">
            Додати
          </button>
        </div>
        <div class="col-md-1">
          <button class="btn btn-outline-success" @click="fetchOrganizers">Усі</button>
        </div>
      </div>
      <br>
      <b-tabs pills card>
        <b-tab title="Запит 7" active>
          <br>
          <div class="row">
            <div class="col-md-3">
              <b-form-select class="mb-2 mr-sm-2 mb-sm-0"
                             v-model="competitionId"
                             placeholder="Спортсмени">
                <option v-for="competition in competitions" :value="competition.competitionId">
                  {{ competition.name}}
                </option>
              </b-form-select>
            </div>
            <div class="col-md-1">
              <button class="btn btn-info" @click="getOrganizerByCompetition(competitionId)">Пошук</button>
            </div>
          </div>
        </b-tab>
        <b-tab title="Усі" @click="fetchOrganizers" active>
        </b-tab>
      </b-tabs>

      <b-card  v-for="organizer in filteredList" :key="organizer.organizerId" v-bind:data="organizer" :title="organizer.lastName"
               img-src="../static/img/organizer.jpg"
               img-alt="Image"
               img-top
               tag="article"
               style="max-width: 20rem;"
               class="mb-2">
        <p class="card-text">
        <p>{{ organizer.firstName}}</p>
        <p>{{ organizer.middleName}}</p>
        <p>{{fetchCountCompetitions(organizer.organizerId)}}</p>
        </p>
        <b-button variant="success" @click="showUpdateModals(organizer)">Змінити</b-button>
        <b-button @click="deleteOrganizer(organizer.organizerId)" variant="danger">Видалити</b-button>
      </b-card>

      <!-- Modal -->
      <div v-if="showModal">
        <transition name="modal">
          <div class="modal-mask">
            <div class="modal-wrapper">
              <div class="modal-dialog sss">
                <div class="modal-content">
                  <div class="modal-header">
                    Додати
                    <button type="button" class="close" @click="showModal=false">
                      <span aria-hidden="true">&times;</span>
                    </button>

                  </div>
                  <div class="modal-body">

                    <div class="form-group">
                      <label>Прізвище</label>
                      <input v-model="formAdd.lastName" type="text" class="form-control" placeholder="Введіть прізвище">
                    </div>

                    <div class="form-group">
                      <label>Ім'я</label>
                      <input v-model="formAdd.firstName" class="form-control" placeholder="Введіть ім'я">
                    </div>

                    <div class="form-group">
                      <label>По батькові</label>
                      <input v-model="formAdd.middleName" class="form-control" placeholder="Введіть по батькові">
                    </div>

                    <button class="btn btn-warning" @click="showModal=false">Скасувати</button>
                    <button v-if="formAdd.lastName !== '' && formAdd.firstName !== ''"
                            class="btn btn-success" @click="addOrganizer">Зберегти</button>
                    <button v-else class="btn btn-success" disabled>Зберегти</button>

                  </div>
                </div>
              </div>
            </div>
          </div>
        </transition>
      </div>

      <div v-if="showUpdateModal">
        <transition name="modal">
          <div class="modal-mask">
            <div class="modal-wrapper">
              <div class="modal-dialog sss">
                <div class="modal-content">
                  <div class="modal-header">
                    Додати
                    <button type="button" class="close" @click="showUpdateModal=false">
                      <span aria-hidden="true">&times;</span>
                    </button>

                  </div>
                  <div class="modal-body">

                    <div class="form-group">
                      <label>Прізвище</label>
                      <input v-model="formAdd.lastName" type="text" class="form-control" placeholder="Введіть прізвище">
                    </div>

                    <div class="form-group">
                      <label>Ім'я</label>
                      <input v-model="formAdd.firstName" class="form-control" placeholder="Введіть ім'я">
                    </div>

                    <div class="form-group">
                      <label>По батькові</label>
                      <input v-model="formAdd.middleName" class="form-control" placeholder="Введіть по батькові">
                    </div>

                    <button class="btn btn-warning" @click="showUpdateModal=false">Скасувати</button>
                    <button v-if="formAdd.lastName !== '' && formAdd.firstName !== ''"
                            class="btn btn-success" @click="updateOrganizer(formAdd)">Зберегти</button>
                    <button v-else class="btn btn-success" disabled>Зберегти</button>

                  </div>
                </div>
              </div>
            </div>
          </div>
        </transition>
      </div>


    </div>
</template>

<script>
  import axios from 'axios'
    export default {
        name: "Organizer",
      data: () => ({
        search: '',
        organizers: [],
        competitions: [],
        competitionId: {},
        formAdd: {},
        showModal: false,
        showUpdateModal: false,
        count: {}
      }),
      created() {
        this.fetchOrganizers();
        this.fetchCompetitions();
      },
      methods: {
        fetchOrganizers() {
          axios.get('/api/organizers').then((response) => {
            this.organizers = response.data;
          })
        },
        fetchCompetitions() {
          axios.get('/api/competitions').then((response) => {
            this.competitions = response.data;
          })
        },
        fetchCountCompetitions(organizerId) {
          axios.get('/api/competition-count/' + organizerId).then((response) => {
            return response.data;
            //console.log(this.count);
          });
        },
        addOrganizer() {
          this.showModal = false;
          axios.post('/api/organizers', this.formAdd).then(() => {
            this.fetchOrganizers();
          });
          this.formAdd = {};
        },
        updateOrganizer(organizer) {
          this.showUpdateModal = false;
          axios.put('/api/organizers/' + organizer.organizerId, this.formAdd).then(() => {
            this.fetchOrganizers();
          });
          this.formAdd = {};
        },
        deleteOrganizer(id) {
          axios.delete('/api/organizers/' + id).then(() => {
            this.fetchOrganizers();
          });
        },
        getOrganizerByCompetition(competitionId) {
          axios.get('/api/organizers-by-competition/' + competitionId).then((response) => {
            this.organizers = response.data;
          })
        },
        showUpdateModals(organizer) {
          this.showUpdateModal = true;
          this.formAdd = organizer;
        },
      },
      computed: {
        filteredList() {
          return this.organizers.filter(organizer => {
            return organizer.lastName.toLowerCase().includes(this.search.toLowerCase());
          })
        },
      }
    }
</script>

<style scoped>
  .container {
    margin-top: 20px;
  }
  .card {
    display: inline-block;
    margin: 8px;
  }

  .modal-mask {
    position: fixed;
    z-index: 9998;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, .5);
    display: table;
    transition: opacity .3s ease;
    overflow-y: scroll;
  }

  .modal-wrapper {
    display: table-cell;
    vertical-align: middle;
  }
</style>
