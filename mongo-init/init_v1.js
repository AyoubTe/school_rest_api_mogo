// Connexion à la base school_db avec les credentials root
db = db.getSiblingDB('school_db');

// ── schools ──────────────────────────────────────────────────
db.createCollection('schools', {
    validator: {
        $jsonSchema: {
            bsonType: 'object',
            required: ['school_name'],
            properties: {
                school_name: { bsonType: 'string', description: 'Required' },
                school_address: { bsonType: 'string' }
            }
        }
    }
});

// ── admins ───────────────────────────────────────────────────
db.createCollection('admins', {
    validator: {
        $jsonSchema: {
            bsonType: 'object',
            required: ['username', 'email', 'password', 'role'],
            properties: {
                username:  { bsonType: 'string' },
                firstname: { bsonType: 'string' },
                lastname:  { bsonType: 'string' },
                email:     { bsonType: 'string' },
                password:  { bsonType: 'string' },
                mission:   { bsonType: 'string' },
                role:      { bsonType: 'string', enum: ['ADMIN'] }
            }
        }
    }
});
db.admins.createIndex({ username: 1 }, { unique: true });
db.admins.createIndex({ email: 1 },    { unique: true });

// ── teachers ─────────────────────────────────────────────────
db.createCollection('teachers', {
    validator: {
        $jsonSchema: {
            bsonType: 'object',
            required: ['username', 'email', 'password', 'role'],
            properties: {
                username:   { bsonType: 'string' },
                firstname:  { bsonType: 'string' },
                lastname:   { bsonType: 'string' },
                email:      { bsonType: 'string' },
                password:   { bsonType: 'string' },
                discipline: { bsonType: 'string' },
                role:       { bsonType: 'string', enum: ['TEACHER'] },
                // Référence à school embarquée
                school: {
                    bsonType: 'object',
                    properties: {
                        _id:  { bsonType: 'objectId' },
                        name: { bsonType: 'string' }
                    }
                }
            }
        }
    }
});
db.teachers.createIndex({ username: 1 }, { unique: true });
db.teachers.createIndex({ email: 1 },    { unique: true });

// ── assignments ───────────────────────────────────────────────
// Collection indépendante — partagée, référencée depuis students
db.createCollection('assignments', {
    validator: {
        $jsonSchema: {
            bsonType: 'object',
            required: ['assignment_name'],
            properties: {
                assignment_name: { bsonType: 'string' },
                description:     { bsonType: 'string' },
                due_date:        { bsonType: 'date' }
            }
        }
    }
});

// ── students ──────────────────────────────────────────────────
// Dénormalisé : cours, notes et devoirs embarqués directement
db.createCollection('students', {
    validator: {
        $jsonSchema: {
            bsonType: 'object',
            required: ['username', 'email', 'password', 'role'],
            properties: {
                username:  { bsonType: 'string' },
                firstname: { bsonType: 'string' },
                lastname:  { bsonType: 'string' },
                email:     { bsonType: 'string' },
                password:  { bsonType: 'string' },
                role:      { bsonType: 'string', enum: ['STUDENT'] },

                // Tableau de cours avec notes embarquées
                courses: {
                    bsonType: 'array',
                    items: {
                        bsonType: 'object',
                        properties: {
                            course_id:   { bsonType: 'objectId' },
                            course_code: { bsonType: 'string' },
                            course_name: { bsonType: 'string' },
                            grade_details: {
                                bsonType: 'object',
                                properties: {
                                    first_grade:  { bsonType: 'int' },
                                    second_grade: { bsonType: 'int' },
                                    third_grade:  { bsonType: 'int' }
                                }
                            }
                        }
                    }
                },

                // Tableau de devoirs avec statut embarqué
                assignment_details: {
                    bsonType: 'array',
                    items: {
                        bsonType: 'object',
                        properties: {
                            assignment_id: { bsonType: 'objectId' },
                            is_done:       { bsonType: 'bool' }
                        }
                    }
                }
            }
        }
    }
});
db.students.createIndex({ username: 1 }, { unique: true });
db.students.createIndex({ email: 1 },    { unique: true });

// ── courses ───────────────────────────────────────────────────
// Collection indépendante — référencée depuis students et teachers
db.createCollection('courses', {
    validator: {
        $jsonSchema: {
            bsonType: 'object',
            required: ['course_code', 'course_name'],
            properties: {
                course_code: { bsonType: 'string' },
                course_name: { bsonType: 'string' },
                teacher: {
                    bsonType: 'object',
                    properties: {
                        _id:      { bsonType: 'objectId' },
                        username: { bsonType: 'string' }
                    }
                }
            }
        }
    }
});
db.courses.createIndex({ course_code: 1 }, { unique: true });

print('school_db initialized with all collections and indexes.');

// =============================================================================
// MongoDB seed script
// All passwords = "password123" (BCrypt hashed)
// =============================================================================

const PWD = '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y';

// ── Clean existing data ───────────────────────────────────────────────────────
db.schools.deleteMany({});
db.admins.deleteMany({});
db.teachers.deleteMany({});
db.students.deleteMany({});
db.courses.deleteMany({});
db.assignments.deleteMany({});

print('Collections cleared');

// =============================================================================
// 1. SCHOOLS
// =============================================================================
db.schools.insertMany([
    { _id: ObjectId(), school_name: 'Lycée Victor Hugo',           school_address: '12 Avenue des Champs-Élysées, Paris' },
    { _id: ObjectId(), school_name: 'Collège Jean Moulin',         school_address: '45 Rue de la République, Lyon' },
    { _id: ObjectId(), school_name: 'École Primaire Marie Curie',  school_address: '8 Boulevard Pasteur, Bordeaux' },
    { _id: ObjectId(), school_name: 'Institut Supérieur des Sciences', school_address: '12 Rue de la Recherche, Lyon' },
    { _id: ObjectId(), school_name: 'Institut Numérique du Sud',   school_address: '12 Rue des Lumières, Lyon' },
]);

const schoolHugo    = db.schools.findOne({ school_name: 'Lycée Victor Hugo' });
const schoolMoulin  = db.schools.findOne({ school_name: 'Collège Jean Moulin' });
const schoolCurie   = db.schools.findOne({ school_name: 'École Primaire Marie Curie' });
const schoolISS     = db.schools.findOne({ school_name: 'Institut Supérieur des Sciences' });
const schoolINS     = db.schools.findOne({ school_name: 'Institut Numérique du Sud' });

print('Schools inserted: ' + db.schools.countDocuments());

// =============================================================================
// 2. ADMINS
// =============================================================================
db.admins.insertMany([
    // V5
    { username: 'admin.chief',      firstname: 'Paul',     lastname: 'Directeur', email: 'paul.chief@school.net',      password: PWD, role: 'ADMIN', mission: 'Supervision globale' },
    { username: 'admin.network',    firstname: 'Alice',    lastname: 'Tech',      email: 'alice.net@school.net',       password: PWD, role: 'ADMIN', mission: 'Gestion du réseau et sécurité' },
    { username: 'admin.payroll',    firstname: 'Bob',      lastname: 'Ressources',email: 'bob.pay@school.net',         password: PWD, role: 'ADMIN', mission: 'Gestion de la paie' },
    { username: 'admin.admissions', firstname: 'Claire',   lastname: 'Scolaire',  email: 'claire.adm@school.net',      password: PWD, role: 'ADMIN', mission: 'Gestion des admissions' },
    { username: 'admin.audit',      firstname: 'Denis',    lastname: 'Compta',    email: 'denis.audit@school.net',     password: PWD, role: 'ADMIN', mission: 'Audit financier' },
    // V6
    { username: 'admin.dupont',     firstname: 'Pierre',   lastname: 'Dupont',    email: 'pierre.dupont@school.net',   password: PWD, role: 'ADMIN', mission: 'Supervision pédagogique' },
    { username: 'admin.martin',     firstname: 'Claire',   lastname: 'Martin',    email: 'claire.martin@school.net',   password: PWD, role: 'ADMIN', mission: 'Gestion infrastructure' },
    { username: 'admin.leroy',      firstname: 'Thomas',   lastname: 'Leroy',     email: 'thomas.leroy@school.net',    password: PWD, role: 'ADMIN', mission: 'Relations avec les professeurs' },
    { username: 'admin.bernard',    firstname: 'Nathalie', lastname: 'Bernard',   email: 'nathalie.bernard@school.net',password: PWD, role: 'ADMIN', mission: 'Suivi des inscriptions' },
    { username: 'admin.petit',      firstname: 'François', lastname: 'Petit',     email: 'francois.petit@school.net',  password: PWD, role: 'ADMIN', mission: 'Gestion budgétaire' },
]);

db.admins.createIndex({ username: 1 }, { unique: true });
db.admins.createIndex({ email: 1 },    { unique: true });
print('Admins inserted: ' + db.admins.countDocuments());

// =============================================================================
// 3. TEACHERS
// =============================================================================
db.teachers.insertMany([
    // V2 — linked to schools via V4
    { username: 'prof.martin',   firstname: 'Paul',     lastname: 'Martin',   email: 'paul.martin@school.net',    password: PWD, role: 'TEACHER', discipline: 'Mathématiques Avancées',         school: { _id: schoolHugo._id,   name: schoolHugo.school_name } },
    { username: 'prof.bernard',  firstname: 'Sophie',   lastname: 'Bernard',  email: 'sophie.bernard@school.net', password: PWD, role: 'TEACHER', discipline: 'Physique Fondamentale',           school: { _id: schoolHugo._id,   name: schoolHugo.school_name } },
    { username: 'prof.thomas',   firstname: 'Luc',      lastname: 'Thomas',   email: 'luc.thomas@school.net',     password: PWD, role: 'TEACHER', discipline: 'Littérature Française',           school: { _id: schoolHugo._id,   name: schoolHugo.school_name } },
    { username: 'prof.petit',    firstname: 'Emma',     lastname: 'Petit',    email: 'emma.petit@school.net',     password: PWD, role: 'TEACHER', discipline: 'Anglais LV1',                    school: { _id: schoolHugo._id,   name: schoolHugo.school_name } },
    { username: 'prof.robert',   firstname: 'Marc',     lastname: 'Robert',   email: 'marc.robert@school.net',    password: PWD, role: 'TEACHER', discipline: 'Histoire Contemporaine',          school: { _id: schoolMoulin._id, name: schoolMoulin.school_name } },
    { username: 'prof.richard',  firstname: 'Julie',    lastname: 'Richard',  email: 'julie.richard@school.net',  password: PWD, role: 'TEACHER', discipline: 'Biologie et Génétique',           school: { _id: schoolMoulin._id, name: schoolMoulin.school_name } },
    { username: 'prof.durand',   firstname: 'Antoine',  lastname: 'Durand',   email: 'antoine.durand@school.net', password: PWD, role: 'TEACHER', discipline: "Introduction à l'Informatique",  school: { _id: schoolMoulin._id, name: schoolMoulin.school_name } },
    { username: 'prof.dubois',   firstname: 'Céline',   lastname: 'Dubois',   email: 'celine.dubois@school.net',  password: PWD, role: 'TEACHER', discipline: 'Sciences Économiques et Sociales',school: { _id: schoolCurie._id,  name: schoolCurie.school_name } },
    { username: 'prof.moreau',   firstname: 'Pierre',   lastname: 'Moreau',   email: 'pierre.moreau@school.net',  password: PWD, role: 'TEACHER', discipline: 'Arts Plastiques',                school: { _id: schoolCurie._id,  name: schoolCurie.school_name } },
    { username: 'prof.laurent',  firstname: 'Alice',    lastname: 'Laurent',  email: 'alice.laurent@school.net',  password: PWD, role: 'TEACHER', discipline: 'Éducation Physique et Sportive', school: { _id: schoolCurie._id,  name: schoolCurie.school_name } },
    // V5
    { username: 'prof.einstein', firstname: 'Albert',   lastname: 'Physique', email: 'a.einstein@school.net',     password: PWD, role: 'TEACHER', discipline: 'Relativité',                     school: { _id: schoolISS._id,    name: schoolISS.school_name } },
    { username: 'prof.lovelace', firstname: 'Ada',      lastname: 'Code',     email: 'a.lovelace@school.net',     password: PWD, role: 'TEACHER', discipline: 'Algorithmique',                  school: { _id: schoolISS._id,    name: schoolISS.school_name } },
    { username: 'prof.nietzsche',firstname: 'Friedrich',lastname: 'Philo',    email: 'f.nietzsche@school.net',    password: PWD, role: 'TEACHER', discipline: 'Philosophie Moderne',             school: { _id: schoolISS._id,    name: schoolISS.school_name } },
    // V6
    { username: 'prof.hopper',   firstname: 'Grace',    lastname: 'Hopper',   email: 'grace.hopper@school.net',   password: PWD, role: 'TEACHER', discipline: 'Mathématiques',                  school: { _id: schoolINS._id,    name: schoolINS.school_name } },
    { username: 'prof.pasteur',  firstname: 'Louis',    lastname: 'Pasteur',  email: 'louis.pasteur@school.net',  password: PWD, role: 'TEACHER', discipline: 'Biologie Évolutive',              school: { _id: schoolINS._id,    name: schoolINS.school_name } },
    { username: 'prof.feynman',  firstname: 'Richard',  lastname: 'Feynman',  email: 'richard.feynman@school.net',password: PWD, role: 'TEACHER', discipline: 'Physique Théorique',              school: { _id: schoolINS._id,    name: schoolINS.school_name } },
]);

db.teachers.createIndex({ username: 1 }, { unique: true });
db.teachers.createIndex({ email: 1 },    { unique: true });
print('Teachers inserted: ' + db.teachers.countDocuments());

// =============================================================================
// 4. COURSES
// =============================================================================
const tMartin    = db.teachers.findOne({ username: 'prof.martin' });
const tBernard   = db.teachers.findOne({ username: 'prof.bernard' });
const tThomas    = db.teachers.findOne({ username: 'prof.thomas' });
const tPetit     = db.teachers.findOne({ username: 'prof.petit' });
const tRobert    = db.teachers.findOne({ username: 'prof.robert' });
const tEinstein  = db.teachers.findOne({ username: 'prof.einstein' });
const tLovelace  = db.teachers.findOne({ username: 'prof.lovelace' });
const tNietzsche = db.teachers.findOne({ username: 'prof.nietzsche' });
const tHopper    = db.teachers.findOne({ username: 'prof.hopper' });
const tPasteur   = db.teachers.findOne({ username: 'prof.pasteur' });
const tFeynman   = db.teachers.findOne({ username: 'prof.feynman' });

db.courses.insertMany([
    // V2 + V4 teacher assignments
    { course_code: 'MAT101',   course_name: 'Mathématiques Avancées',           teacher: { _id: tMartin._id,    username: tMartin.username } },
    { course_code: 'PHY101',   course_name: 'Physique Fondamentale',             teacher: { _id: tMartin._id,    username: tMartin.username } },
    { course_code: 'CHM101',   course_name: 'Chimie Organique',                  teacher: { _id: tMartin._id,    username: tMartin.username } },
    { course_code: 'BIO101',   course_name: 'Biologie et Génétique',             teacher: { _id: tBernard._id,   username: tBernard.username } },
    { course_code: 'HIS101',   course_name: 'Histoire Contemporaine',            teacher: { _id: tBernard._id,   username: tBernard.username } },
    { course_code: 'GEO101',   course_name: 'Géographie Mondiale',               teacher: { _id: tBernard._id,   username: tBernard.username } },
    { course_code: 'ENG101',   course_name: 'Anglais LV1',                       teacher: { _id: tThomas._id,    username: tThomas.username } },
    { course_code: 'ESP101',   course_name: 'Espagnol LV2',                      teacher: { _id: tThomas._id,    username: tThomas.username } },
    { course_code: 'FRA101',   course_name: 'Littérature Française',             teacher: { _id: tThomas._id,    username: tThomas.username } },
    { course_code: 'INF101',   course_name: "Introduction à l'Informatique",     teacher: { _id: tPetit._id,     username: tPetit.username } },
    { course_code: 'ART101',   course_name: 'Arts Plastiques',                   teacher: { _id: tPetit._id,     username: tPetit.username } },
    { course_code: 'MUS101',   course_name: 'Éducation Musicale',                teacher: { _id: tPetit._id,     username: tPetit.username } },
    { course_code: 'SPO101',   course_name: 'Éducation Physique et Sportive',    teacher: { _id: tRobert._id,    username: tRobert.username } },
    { course_code: 'ECO101',   course_name: 'Sciences Économiques et Sociales',  teacher: { _id: tRobert._id,    username: tRobert.username } },
    // V5
    { course_code: 'PHY-202',  course_name: 'Physique Relativiste',              teacher: { _id: tEinstein._id,  username: tEinstein.username } },
    { course_code: 'CS-303',   course_name: 'Structures de données',             teacher: { _id: tLovelace._id,  username: tLovelace.username } },
    { course_code: 'PHI-404',  course_name: 'Philosophie du 19ème siècle',       teacher: { _id: tNietzsche._id, username: tNietzsche.username } },
    // V6
    { course_code: 'MATH-301', course_name: 'Algèbre et Logique Formelle',       teacher: { _id: tHopper._id,    username: tHopper.username } },
    { course_code: 'BIO-101',  course_name: 'Évolution et Génétique',            teacher: { _id: tPasteur._id,   username: tPasteur.username } },
    { course_code: 'PHY-303',  course_name: 'Mécanique Quantique Avancée',       teacher: { _id: tFeynman._id,   username: tFeynman.username } },
]);

db.courses.createIndex({ course_code: 1 }, { unique: true });
print('Courses inserted: ' + db.courses.countDocuments());

// =============================================================================
// 5. ASSIGNMENTS
// =============================================================================
db.assignments.insertMany([
    // V3
    { assignment_name: 'Devoir Maison de Mathématiques', description: 'Résoudre les équations du chapitre 4.',           due_date: new Date('2026-04-15T08:00:00') },
    { assignment_name: "Dissertation d'Histoire",         description: 'Les causes de la révolution industrielle.',       due_date: new Date('2026-04-20T12:00:00') },
    { assignment_name: "Projet d'Informatique",           description: 'Créer une API REST avec Spring Boot.',            due_date: new Date('2026-05-01T23:59:00') },
    { assignment_name: "Exposé d'Anglais",                description: 'Présenter un pays anglophone de votre choix.',    due_date: new Date('2026-04-18T10:00:00') },
    // V5
    { assignment_name: 'Devoir Relativité',               description: 'Calcul de la dilatation du temps',                due_date: new Date('2026-06-15T23:59:00') },
    { assignment_name: 'Projet Algorithme',               description: 'Implémenter un arbre binaire en Java',            due_date: new Date('2026-07-01T23:59:00') },
    // V6
    { assignment_name: 'Devoir Maths 1',                  description: 'Démontrer le théorème de Gödel par récurrence',   due_date: new Date('2026-05-20T23:59:00') },
    { assignment_name: 'Rapport Biologie',                description: "Analyse comparative des mécanismes d'adaptation génétique", due_date: new Date('2026-06-10T23:59:00') },
]);
print('✅ Assignments inserted: ' + db.assignments.countDocuments());

// =============================================================================
// 6. STUDENTS with embedded courses + grades + assignment_details
// =============================================================================

// Course references
const cMAT101  = db.courses.findOne({ course_code: 'MAT101' });
const cPHY101  = db.courses.findOne({ course_code: 'PHY101' });
const cCHM101  = db.courses.findOne({ course_code: 'CHM101' });
const cBIO101  = db.courses.findOne({ course_code: 'BIO101' });
const cHIS101  = db.courses.findOne({ course_code: 'HIS101' });
const cGEO101  = db.courses.findOne({ course_code: 'GEO101' });
const cENG101  = db.courses.findOne({ course_code: 'ENG101' });
const cESP101  = db.courses.findOne({ course_code: 'ESP101' });
const cFRA101  = db.courses.findOne({ course_code: 'FRA101' });
const cINF101  = db.courses.findOne({ course_code: 'INF101' });
const cART101  = db.courses.findOne({ course_code: 'ART101' });
const cMUS101  = db.courses.findOne({ course_code: 'MUS101' });
const cSPO101  = db.courses.findOne({ course_code: 'SPO101' });
const cECO101  = db.courses.findOne({ course_code: 'ECO101' });
const cPHY202  = db.courses.findOne({ course_code: 'PHY-202' });
const cCS303   = db.courses.findOne({ course_code: 'CS-303' });
const cPHI404  = db.courses.findOne({ course_code: 'PHI-404' });
const cMATH301 = db.courses.findOne({ course_code: 'MATH-301' });
const cBIO101b = db.courses.findOne({ course_code: 'BIO-101' });
const cPHY303  = db.courses.findOne({ course_code: 'PHY-303' });

// Assignment references
const aMaths  = db.assignments.findOne({ assignment_name: 'Devoir Maison de Mathématiques' });
const aHist   = db.assignments.findOne({ assignment_name: "Dissertation d'Histoire" });
const aInfo   = db.assignments.findOne({ assignment_name: "Projet d'Informatique" });
const aAngl   = db.assignments.findOne({ assignment_name: "Exposé d'Anglais" });
const aRel    = db.assignments.findOne({ assignment_name: 'Devoir Relativité' });
const aAlgo   = db.assignments.findOne({ assignment_name: 'Projet Algorithme' });
const aMath1  = db.assignments.findOne({ assignment_name: 'Devoir Maths 1' });
const aBio    = db.assignments.findOne({ assignment_name: 'Rapport Biologie' });

// Helper to build a course enrollment with grades
function enroll(course, g1, g2, g3) {
    return {
        course_id:   course._id,
        course_code: course.course_code,
        course_name: course.course_name,
        grade_details: { first_grade: g1, second_grade: g2, third_grade: g3 }
    };
}

// Helper to build an assignment_detail entry
function adet(assignment, is_done) {
    return { assignment_id: assignment._id, assignment_name: assignment.assignment_name, is_done: is_done };
}

db.students.insertMany([
    // V2 students
    { username: 'etu.simon',    firstname: 'Lucas',   lastname: 'Simon',    email: 'lucas.simon@student.net',    password: PWD, role: 'STUDENT',
        courses: [ enroll(cMAT101,14,15,16), enroll(cPHY101,10,12,11), enroll(cCHM101,18,17,19) ],
        assignment_details: [ adet(aMaths,true), adet(aHist,false), adet(aInfo,false) ] },

    { username: 'etu.michel',   firstname: 'Léa',     lastname: 'Michel',   email: 'lea.michel@student.net',     password: PWD, role: 'STUDENT',
        courses: [ enroll(cMAT101,9,10,12), enroll(cBIO101,15,14,16), enroll(cHIS101,11,13,12) ],
        assignment_details: [ adet(aMaths,true), adet(aHist,true), adet(aAngl,false) ] },

    { username: 'etu.lefebvre', firstname: 'Hugo',    lastname: 'Lefebvre', email: 'hugo.lefebvre@student.net',  password: PWD, role: 'STUDENT',
        courses: [ enroll(cENG101,16,18,17), enroll(cESP101,12,12,13), enroll(cFRA101,19,20,18) ],
        assignment_details: [ adet(aMaths,false), adet(aHist,false), adet(aInfo,false) ] },

    { username: 'etu.leroy',    firstname: 'Chloé',   lastname: 'Leroy',    email: 'chloe.leroy@student.net',    password: PWD, role: 'STUDENT',
        courses: [ enroll(cINF101,8,9,10), enroll(cART101,14,14,15), enroll(cMUS101,13,11,14) ],
        assignment_details: [ adet(aInfo,true), adet(aAngl,true) ] },

    { username: 'etu.roux',     firstname: 'Arthur',  lastname: 'Roux',     email: 'arthur.roux@student.net',    password: PWD, role: 'STUDENT',
        courses: [ enroll(cMAT101,17,16,18), enroll(cSPO101,10,10,11), enroll(cECO101,15,16,15) ],
        assignment_details: [ adet(aMaths,true), adet(aInfo,false) ] },

    { username: 'etu.david',    firstname: 'Camille', lastname: 'David',    email: 'camille.david@student.net',  password: PWD, role: 'STUDENT',
        courses: [ enroll(cPHY101,12,14,13) ],
        assignment_details: [ adet(aMaths,true) ] },

    { username: 'etu.bertrand', firstname: 'Enzo',    lastname: 'Bertrand', email: 'enzo.bertrand@student.net',  password: PWD, role: 'STUDENT',
        courses: [ enroll(cCHM101,18,19,19) ],
        assignment_details: [ adet(aHist,true) ] },

    { username: 'etu.morel',    firstname: 'Inès',    lastname: 'Morel',    email: 'ines.morel@student.net',     password: PWD, role: 'STUDENT',
        courses: [ enroll(cBIO101,11,10,12) ],
        assignment_details: [ adet(aInfo,false) ] },

    { username: 'etu.fournier', firstname: 'Nathan',  lastname: 'Fournier', email: 'nathan.fournier@student.net',password: PWD, role: 'STUDENT',
        courses: [ enroll(cHIS101,16,15,17) ],
        assignment_details: [ adet(aAngl,true) ] },

    { username: 'etu.girard',   firstname: 'Manon',   lastname: 'Girard',   email: 'manon.girard@student.net',   password: PWD, role: 'STUDENT',
        courses: [ enroll(cGEO101,14,13,15) ],
        assignment_details: [ adet(aMaths,false) ] },

    { username: 'etu.bonnet',   firstname: 'Mathis',  lastname: 'Bonnet',   email: 'mathis.bonnet@student.net',  password: PWD, role: 'STUDENT',
        courses: [], assignment_details: [] },

    { username: 'etu.dupont',   firstname: 'Clara',   lastname: 'Dupont',   email: 'clara.dupont@student.net',   password: PWD, role: 'STUDENT',
        courses: [], assignment_details: [] },

    { username: 'etu.lambert',  firstname: 'Jules',   lastname: 'Lambert',  email: 'jules.lambert@student.net',  password: PWD, role: 'STUDENT',
        courses: [], assignment_details: [] },

    { username: 'etu.fontaine', firstname: 'Sarah',   lastname: 'Fontaine', email: 'sarah.fontaine@student.net', password: PWD, role: 'STUDENT',
        courses: [], assignment_details: [] },

    { username: 'etu.rousseau', firstname: 'Gabin',   lastname: 'Rousseau', email: 'gabin.rousseau@student.net', password: PWD, role: 'STUDENT',
        courses: [], assignment_details: [] },

    { username: 'etu.vincent',  firstname: 'Margaux', lastname: 'Vincent',  email: 'margaux.vincent@student.net',password: PWD, role: 'STUDENT',
        courses: [], assignment_details: [] },

    { username: 'etu.muller',   firstname: 'Raphaël', lastname: 'Muller',   email: 'raphael.muller@student.net', password: PWD, role: 'STUDENT',
        courses: [], assignment_details: [] },

    { username: 'etu.lefevre',  firstname: 'Jade',    lastname: 'Lefevre',  email: 'jade.lefevre@student.net',   password: PWD, role: 'STUDENT',
        courses: [], assignment_details: [] },

    { username: 'etu.faure',    firstname: 'Maël',    lastname: 'Faure',    email: 'mael.faure@student.net',     password: PWD, role: 'STUDENT',
        courses: [], assignment_details: [] },

    { username: 'etu.andre',    firstname: 'Louise',  lastname: 'Andre',    email: 'louise.andre@student.net',   password: PWD, role: 'STUDENT',
        courses: [], assignment_details: [] },

    // V5 students
    { username: 'etu.link',     firstname: 'Link',    lastname: 'Hero',     email: 'link.h@student.net',         password: PWD, role: 'STUDENT',
        courses: [ enroll(cPHY202,15,17,16) ],
        assignment_details: [ adet(aRel,false) ] },

    { username: 'etu.samus',    firstname: 'Samus',   lastname: 'Aran',     email: 'samus.a@student.net',        password: PWD, role: 'STUDENT',
        courses: [ enroll(cCS303,19,20,18) ],
        assignment_details: [ adet(aAlgo,true) ] },

    { username: 'etu.kratos',   firstname: 'Kratos',  lastname: 'Sparta',   email: 'kratos.s@student.net',       password: PWD, role: 'STUDENT',
        courses: [ enroll(cPHI404,11,13,12) ],
        assignment_details: [] },

    { username: 'etu.cloud',    firstname: 'Cloud',   lastname: 'Strife',   email: 'cloud.s@student.net',        password: PWD, role: 'STUDENT',
        courses: [ enroll(cCS303,20,19,19) ],
        assignment_details: [] },

    // V6 students
    { username: 'etu.geralt',   firstname: 'Geralt',  lastname: 'Rivia',    email: 'geralt.r@student.net',       password: PWD, role: 'STUDENT',
        courses: [ enroll(cMATH301,17,15,16) ],
        assignment_details: [ adet(aMath1,false) ] },

    { username: 'etu.aloy',     firstname: 'Aloy',    lastname: 'Nora',     email: 'aloy.n@student.net',         password: PWD, role: 'STUDENT',
        courses: [ enroll(cBIO101b,12,14,13) ],
        assignment_details: [ adet(aBio,true) ] },

    { username: 'etu.jill',     firstname: 'Jill',    lastname: 'Valentine',email: 'jill.v@student.net',         password: PWD, role: 'STUDENT',
        courses: [ enroll(cPHY303,19,20,18) ],
        assignment_details: [] },

    { username: 'etu.cortana',  firstname: 'Cortana', lastname: 'Spartan',  email: 'cortana.s@student.net',      password: PWD, role: 'STUDENT',
        courses: [ enroll(cMATH301,11,10,12) ],
        assignment_details: [] },
]);

db.students.createIndex({ username: 1 }, { unique: true });
db.students.createIndex({ email: 1 },    { unique: true });
print('Students inserted: ' + db.students.countDocuments());

print('');
print('school_db fully seeded!');
print('   Schools:     ' + db.schools.countDocuments());
print('   Admins:      ' + db.admins.countDocuments());
print('   Teachers:    ' + db.teachers.countDocuments());
print('   Courses:     ' + db.courses.countDocuments());
print('   Assignments: ' + db.assignments.countDocuments());
print('   Students:    ' + db.students.countDocuments());
