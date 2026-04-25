// Connexion à la base school_db
db = db.getSiblingDB('school_db');

// Nettoyage complet pour éviter les conflits de schémas
db.dropDatabase();

print('Création des collections et des index...');

db.createCollection('schools');
db.createCollection('admins');
db.admins.createIndex({ username: 1 }, { unique: true });
db.admins.createIndex({ email: 1 },    { unique: true });

db.createCollection('teachers');
db.teachers.createIndex({ username: 1 }, { unique: true });
db.teachers.createIndex({ email: 1 },    { unique: true });

db.createCollection('students');
db.students.createIndex({ username: 1 }, { unique: true });
db.students.createIndex({ email: 1 },    { unique: true });

db.createCollection('courses');
db.courses.createIndex({ course_code: 1 }, { unique: true });

db.createCollection('assignments');
db.createCollection('assignment_details');
db.createCollection('grade_details');
db.createCollection('student_course_details');


// =============================================================================
// Injection des données de test
// =============================================================================
const PWD = '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y'; // "password123"

// Fonction utilitaire pour générer la syntaxe DBRef attendue par Spring Boot
function dbRef(collectionName, id) {
    return { "$ref": collectionName, "$id": id };
}

// 1. Pré-génération des IDs (Obligatoire pour les relations circulaires @DBRef)
const sHugoId = ObjectId(), sMoulinId = ObjectId();
const tMartinId = ObjectId(), tBernardId = ObjectId();
const cMatId = ObjectId(), cPhyId = ObjectId();
const stSimonId = ObjectId(), stMichelId = ObjectId();
const aMathsId = ObjectId(), aHistId = ObjectId();
const adMathsId = ObjectId(), adHistId = ObjectId();
const scdSimonMatId = ObjectId(), scdMichelPhyId = ObjectId();
const gdSimonMatId = ObjectId(), gdMichelPhyId = ObjectId();

print('Insertion des données avec @DBRef...');

// 2. Insertions
// -- SCHOOLS --
db.schools.insertMany([
    { _id: sHugoId,   school_name: 'Lycée Victor Hugo',   school_address: 'Paris', teachers: [ dbRef('teachers', tMartinId), dbRef('teachers', tBernardId) ] },
    { _id: sMoulinId, school_name: 'Collège Jean Moulin', school_address: 'Lyon',  teachers: [] }
]);

// -- ADMINS --
db.admins.insertMany([
    { _id: ObjectId(), _class: "net.samiayoub.school_mogodb.entity.Admin", username: 'admin.chief', firstname: 'Paul', lastname: 'Directeur', email: 'paul.chief@school.net', password: PWD, role: 'ADMIN', mission: 'Supervision globale' }
]);

// -- TEACHERS --
db.teachers.insertMany([
    { _id: tMartinId,  _class: "net.samiayoub.school_mogodb.entity.Teacher", username: 'prof.martin', firstname: 'Paul', lastname: 'Martin', email: 'paul.martin@school.net', password: PWD, role: 'TEACHER', discipline: 'Mathématiques', school: dbRef('schools', sHugoId), courses: [ dbRef('courses', cMatId) ] },
    { _id: tBernardId, _class: "net.samiayoub.school_mogodb.entity.Teacher", username: 'prof.bernard', firstname: 'Sophie', lastname: 'Bernard', email: 'sophie.bernard@school.net', password: PWD, role: 'TEACHER', discipline: 'Physique', school: dbRef('schools', sHugoId), courses: [ dbRef('courses', cPhyId) ] }
]);

// -- STUDENTS --
db.students.insertMany([
    { _id: stSimonId,  _class: "net.samiayoub.school_mogodb.entity.Student", username: 'etu.simon', firstname: 'Lucas', lastname: 'Simon', email: 'lucas.simon@student.net', password: PWD, role: 'STUDENT', courses: [ dbRef('courses', cMatId), dbRef('courses', cPhyId) ] },
    { _id: stMichelId, _class: "net.samiayoub.school_mogodb.entity.Student", username: 'etu.michel', firstname: 'Léa', lastname: 'Michel', email: 'lea.michel@student.net', password: PWD, role: 'STUDENT', courses: [ dbRef('courses', cPhyId) ] }
]);

// -- COURSES --
db.courses.insertMany([
    { _id: cMatId, course_code: 'MAT101', course_name: 'Mathématiques Avancées', teacher: dbRef('teachers', tMartinId),  students: [ dbRef('students', stSimonId) ] },
    { _id: cPhyId, course_code: 'PHY101', course_name: 'Physique Fondamentale',  teacher: dbRef('teachers', tBernardId), students: [ dbRef('students', stSimonId), dbRef('students', stMichelId) ] }
]);

// -- ASSIGNMENTS --
db.assignments.insertMany([
    { _id: aMathsId, assignment_name: 'Devoir Maison de Mathématiques', description: 'Chapitre 4.', due_date: new Date('2026-04-15T08:00:00'), details: [ dbRef('assignment_details', adMathsId) ] },
    { _id: aHistId,  assignment_name: "Dissertation d'Histoire",         description: 'Révolution.', due_date: new Date('2026-04-20T12:00:00'), details: [ dbRef('assignment_details', adHistId) ] }
]);

// -- ASSIGNMENT DETAILS --
db.assignment_details.insertMany([
    { _id: adMathsId, is_done: true,  assignment: dbRef('assignments', aMathsId) },
    { _id: adHistId,  is_done: false, assignment: dbRef('assignments', aHistId) }
]);

// -- STUDENT COURSE DETAILS (Tables de jointures conceptualisées) --
// On stocke les IDs en String (toHexString) pour matcher vos entités où vous avez passé les IDs en String
db.student_course_details.insertMany([
    { _id: scdSimonMatId,  student_id: stSimonId.toHexString(),  course_id: cMatId.toHexString(), courses: [ dbRef('courses', cMatId) ], gradeDetails: dbRef('grade_details', gdSimonMatId) },
    { _id: scdMichelPhyId, student_id: stMichelId.toHexString(), course_id: cPhyId.toHexString(), courses: [ dbRef('courses', cPhyId) ], gradeDetails: dbRef('grade_details', gdMichelPhyId) }
]);

// -- GRADE DETAILS --
db.grade_details.insertMany([
    { _id: gdSimonMatId,  first_grade: 14, second_grade: 15, third_grade: 16, studentCourseDetails: dbRef('student_course_details', scdSimonMatId) },
    { _id: gdMichelPhyId, first_grade: 18, second_grade: 17, third_grade: 19, studentCourseDetails: dbRef('student_course_details', scdMichelPhyId) }
]);

print('Base de données school_db correctement remplie et synchronisée avec les @DBRef Java !');